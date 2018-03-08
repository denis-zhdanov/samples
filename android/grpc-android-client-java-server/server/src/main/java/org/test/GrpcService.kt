package org.test

import io.grpc.stub.StreamObserver

class GrpcService : RefreshServiceGrpc.RefreshServiceImplBase() {

    override fun refreshAll(request: RefreshAllRequest, responseObserver: StreamObserver<RefreshAllResponse>) {
        println("Got a request")
        responseObserver.onNext(RefreshAllResponse.newBuilder().setPayload("Response1").build())
        responseObserver.onNext(RefreshAllResponse.newBuilder().setPayload("Response2").build())
        responseObserver.onCompleted()
    }
}