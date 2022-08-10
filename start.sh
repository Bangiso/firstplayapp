docker stop  $(docker ps | grep -w first-play-app | head -n 1  | awk '{print $1;}')
sbt clean compile
sbt docker:publishLocal
docker run --env-file ./envfile -d --rm -p  9000:9000 --name  play-web-app first-play-app
docker commit  $(docker ps | grep -w first-play-app | head -n 1  | awk '{print $1;}')  aphiwe2020/first-play-app:$1
docker push aphiwe2020/first-play-app:$1
kubectl apply -f deployments/deployment.yaml

