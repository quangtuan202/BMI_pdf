package com.tuan.exportpdf;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;

import static androidx.core.content.ContextCompat.startActivity;

public class OpenFile {
    private void openFileWithIntent(Uri fileUri, String typeString, String openTitle) {
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        target.setDataAndType(
                fileUri,
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(typeString)
        ); // For now there is only type 1 (PDF).
        Intent intent = Intent.createChooser(target, openTitle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        try {
            startActivity(this.getApplicationContext(),intent);
        } catch (ActivityNotFoundException e) {
            if (BuildConfig.DEBUG) e.printStackTrace();
            Toast.makeText(context, getString(R.string.error_no_pdf_app), Toast.LENGTH_SHORT).show();
            FirebaseCrashlytics.getInstance().log(getString(R.string.error_no_pdf_app));
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }
}
