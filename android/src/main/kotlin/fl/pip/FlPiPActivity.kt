package fl.pip

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import io.flutter.embedding.android.FlutterFragmentActivity

open class FlPiPActivity : FlutterFragmentActivity() {
    private val pipHelper: PiPHelper = PiPHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pipHelper.setActivity(this, this.applicationContext)
    }

    // Fixed: Use non-nullable Configuration for FlutterFragmentActivity
    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean, 
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        Log.d("FlPiP", "=== onPictureInPictureModeChanged ===")
        Log.d("FlPiP", "isInPictureInPictureMode: $isInPictureInPictureMode")
        try {
            val lifecycleState = lifecycle.currentState.name
            val dismissed = lifecycle.currentState == Lifecycle.State.CREATED
            Log.d("FlPiP", "Lifecycle State: $lifecycleState")
            Log.d("FlPiP", "Dismissed flag: $dismissed")
            pipHelper.onPictureInPictureModeChanged(isInPictureInPictureMode, lifecycleState, dismissed)
        } catch (e: Exception) {
            Log.e("FlPiP", "Error getting lifecycle state: ${e.message}", e)
            pipHelper.onPictureInPictureModeChanged(isInPictureInPictureMode, null, false)
        }
        Log.d("FlPiP", "=== End onPictureInPictureModeChanged ===")
    }

    override fun onPause() {
        super.onPause()
        pipHelper.onActivityPaused()
    }

    override fun onResume() {
        super.onResume()
        pipHelper.onActivityResume()
    }
}