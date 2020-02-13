package util

import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

// 심플 로그캣.
fun logCatSimple(string: Any) {
    Log.d("ljw", string.toString())
}

// 심플 짧은 토스트.
@JvmOverloads //Java 에서 이 함수를 사용시 함수 기본값 처리를 위한 어노테이션.
fun toastShortSimple(string: Any, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MainApplication.appContext, string.toString(), length).show()
}

// 확인 알림창 띄우기.
fun showConfirmDialog(context: Context?, title: String?, message: String?, handler: Handler) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton("Yes") { _, _ -> handler.sendEmptyMessage(1) }
        .setNegativeButton("No") { dialog, _ -> handler.sendEmptyMessage(0); dialog.cancel() }
        .create()
        .show()
}

// 메시지 알림창 띄우기.
fun showMessageDialog(context: Context?, title: String?, message: String?) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton("닫기", null)
        .create()
        .show()
}

//키패드 내리기
fun hideKeypad(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}