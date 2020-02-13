package util

import android.app.Application
import android.content.Context

// 앱 첫 실행시 MainApplication 을 가지는 클래스
// Manifest -> <application /> 안에 android:name="util.MainApplication" 추가해야함.
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        var appContext: Context? = null
    }
}