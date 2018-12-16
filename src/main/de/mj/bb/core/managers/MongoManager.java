package main.de.mj.bb.core.managers;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;

public class MongoManager {

    private MongoClient client;
    private MongoDatabase database;

    private MongoCollection<Document> players;

    public void connect() {
        this.client = MongoClients.create();
        this.database = this.client.getDatabase("core");
        this.players = this.database.getCollection("players");
    }

    public MongoCollection<Document> getPlayers() {
        return players;
    }
}
