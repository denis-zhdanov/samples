package org.test

import io.grpc.ServerBuilder

class Starter {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val grpcPort = 8090
            val serverBuilder = ServerBuilder.forPort(grpcPort)
            serverBuilder.addService(GrpcService())
            val server = serverBuilder.build()
            println("Starting GRPC server on port $grpcPort")
            server.start()
            while (true) {
                Thread.currentThread().join()
            }
        }
    }
}