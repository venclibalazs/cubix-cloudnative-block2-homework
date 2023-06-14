package hu.cubix.cloud.api.controller;

import hu.cubix.cloud.call.api.BackappApi;
import hu.cubix.cloud.call.model.BackappResponse;
import hu.cubix.cloud.model.FrontappResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/frontapp")
public class FrontappController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontappController.class);
    private final BackappApi api;
    private final String homeworkOwner;
    private final String defaultMessage;
    private final String extraImageData;

    public FrontappController(BackappApi api,
                              @Value("${cubix.homework:frontapp-me}") String homeworkOwner,
                              @Value("${app.default.message:DEFAULT-FRONTAPP-MESSAGE}") String defaultMessage,
                              @Value("${extra.from.base:NOT_SET}") String extraImageData) {
        this.api = api;
        this.homeworkOwner = homeworkOwner;
        this.defaultMessage = defaultMessage;
        this.extraImageData = extraImageData;
    }

    @GetMapping("/local")
    public FrontappResponse noCall(@RequestParam(required = false, name = "message") String message) {
        if (!StringUtils.hasText(message)) {
            message = defaultMessage;
        }
        return new FrontappResponse(0L, "frontapp:" + message, homeworkOwner, getHostAddress(),
                null, null, true);
    }

    @GetMapping
    public FrontappResponse call(@RequestParam(required = false, name = "message") String message) {
        if (!StringUtils.hasText(message)) {
            message = defaultMessage;
        }
        LocalDateTime start = LocalDateTime.now();
        BackappResponse backappResponse = api.backapp(message);
        Duration timeBetween = Duration.between(start, backappResponse.time());

        return new FrontappResponse(timeBetween.abs().toMillis(), backappResponse.message(),
                homeworkOwner, getHostAddress(),
                backappResponse.homeworkOwner(), backappResponse.hostAddress(),
                doesExtraImageDataMatch(extraImageData, backappResponse.extraImageData()));
    }

    private boolean doesExtraImageDataMatch(String frontapp, String backapp) {
        if (Objects.equals(frontapp, backapp)) {
            return !frontapp.equals("NOT_SET");
        }
        return false;
    }

    private String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception exception) {
            LOGGER.warn("Unable to determine localhost address", exception);
            return null;
        }
    }
}
