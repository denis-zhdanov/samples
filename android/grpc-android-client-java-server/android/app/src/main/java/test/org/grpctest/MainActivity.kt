package test.org.grpctest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import kotlinx.android.synthetic.main.activity_main.*
import org.test.RefreshAllRequest
import org.test.RefreshAllResponse
import org.test.RefreshServiceGrpc
import java.util.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val mainHandler = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var grpcStub: RefreshServiceGrpc.RefreshServiceStub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val channel = ManagedChannelBuilder
                .forAddress("192.168.100.3", 8090)
                // TODO den remove
//                .forAddress("127.0.0.1", 8090)
                .usePlaintext(true)
                .build()
        grpcStub = RefreshServiceGrpc.newStub(channel)

        call_grpc.setOnClickListener {
            executor.execute {
                val request = RefreshAllRequest.newBuilder().setFrom(Date().toString()).build()
                grpcStub.refreshAll(request, object : StreamObserver<RefreshAllResponse> {
                    override fun onNext(response: RefreshAllResponse) {
                        mainHandler.post {
                            text.append(response.payload + "\n")
                        }
                    }

                    override fun onError(t: Throwable) {
                        mainHandler.post {
                            text.append("Got an error $t\n")
                        }
                    }

                    override fun onCompleted() {
                        mainHandler.post {
                            text.append("Done\n")
                        }
                    }
                })
            }
        }
    }
}
