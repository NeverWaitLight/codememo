# MongoDB

```shell
# Pull image
docker pull mongo
# Create container
docker run -d -p 27017:27017 --name mongodb -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin mongo
# Exec
docker exec -it mongodb bash
```

The mongo shell is removed from MongoDB 6.0. The replacement is `mongosh`