const grpc = require("grpc");

const proto = grpc.load(__dirname + "/../proto/sample.proto");

const client = new proto.sample.SampleService("localhost:50051", grpc.credentials.createInsecure());

client.greet({name: "Jhon"}, (err, answer) => {
  if (err) {
    console.error("Error on calling the remote service: " + JSON.stringify(err));
  } else {
    console.log("Successfully called remote service: " + answer.message);
  }
});
