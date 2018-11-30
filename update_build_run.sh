git pull
mvn clean package
nohup java $* -jar target/bookmarks-1.0-SNAPSHOT.jar > /dev/null &