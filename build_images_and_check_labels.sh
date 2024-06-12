echo build homework-backapp:latest image
docker build -t homework2-backapp:latest -f Dockerfile backapp
echo homework-backapp:latest image build is done

echo build homework-frontapp:latest image
docker build -t homework2-frontapp:latest -f Dockerfile frontapp
echo homework-frontapp:latest image build is done

echo show backapp labels
docker image inspect homework2-backapp:latest -f '{{json .Config.Labels}}'
echo show frontapp labels
docker image inspect homework2-frontapp:latest -f '{{json .Config.Labels}}'