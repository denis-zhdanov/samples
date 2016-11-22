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
