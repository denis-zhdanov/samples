const grpc = require("grpc");

const PROTO_PATH = __dirname + "/../proto/sample.proto";
const proto = grpc.load(PROTO_PATH);

const server = new grpc.Server();
const sampleService = {
  greet: (call, callback) => {
    callback(null, "Hey, " + call.request.name);
  }
};
server.addProtoService(proto.sample.SampleService.service, sampleService);
try {
  server.bind("0.0.0.0:50051", grpc.ServerCredentials.createInsecure());
  server.start();
} catch (e) {
  console.log("An error on gRPC initialization", e)
}

const client = new proto.sample.SampleService("localhost:50051", grpc.credentials.createInsecure());

client.greet({name: "Jhon"}, (err, answer) => {
  if (err) {
    console.error("Error on calling the remote service: " + JSON.stringify(err));
  } else {
    console.log("Successfully called remote service: " + answer.message);
  }
});
