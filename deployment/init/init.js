db = db.getSiblingDB("authdb");

db.createUser({
  user: "mongo",
  pwd: "admin",
  roles: [
    { role: "readWrite", db: "authdb" }
  ]
});

db.createCollection("users");