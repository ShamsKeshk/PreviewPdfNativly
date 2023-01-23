package com.example.scanzxing

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.graphics.pdf.PdfRenderer.Page
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File


class MainActivity : AppCompatActivity() {

    private fun getSeekableFileDescriptor(pdfUri: String): ParcelFileDescriptor {
        //TODO, Send your pdfUri here to this method "ParcelFileDescriptor"
        //=========== Remove This as this for testing only ==========//
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val staticPdfUri = File(path, "Programming.pdf")
        //=========== End of Removing This as this for testing only ==========//

        //Use this only
        return ParcelFileDescriptor.open(staticPdfUri, ParcelFileDescriptor.MODE_READ_ONLY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE),
            10
        )

       findViewById<Button>(R.id.btn_render_pdf).setOnClickListener {
           renderURI("static pdf from my device")
       }

    }

    private fun renderURI(pdfUri: String){
        val renderer = PdfRenderer(getSeekableFileDescriptor(pdfUri))

        // let us just render all pages
        val pageCount = renderer.pageCount
        if (pageCount >= 1){
            val page: Page = renderer.openPage(1)


            val bitmap = Bitmap.createBitmap(200,300,Bitmap.Config.ARGB_8888);
            // say we render for showing on the screen
            page.render(bitmap, null, null, Page.RENDER_MODE_FOR_DISPLAY);

            // do stuff with the bitmap
            findViewById<ImageView>(R.id.iv_pdf_image).setImageBitmap(bitmap)
            // close the page
            page.close();
        }

        // close the renderer
        renderer.close();
    }
}