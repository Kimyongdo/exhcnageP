package exchange.example.newopenapiexchangeproject3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newopenapiexchangeproject3.R;

import im.delight.android.webview.AdvancedWebView;

public class newsWebview extends AppCompatActivity implements AdvancedWebView.Listener{

   // WebView webView;
    private AdvancedWebView mWebView;
    boolean preventCaching = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_newswebview);

        Intent intent = getIntent();
        String link = intent.getStringExtra("Link");

        //얻어온 링크주소를 웹뷰에 보여주기
        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.setListener(this, this);
        mWebView.setThirdPartyCookiesEnabled(false);
        mWebView.setCookiesEnabled(false);
        //크롬사용 - 빠르긴하나 두번 꺼야하는 불편함?
        if (AdvancedWebView.Browsers.hasAlternative(this)) {
            AdvancedWebView.Browsers.openUrl(this, link);
        }

    }


    @Override
    protected void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
