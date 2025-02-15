package com.benny.pxerstudio.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.text.parseAsHtml
import com.benny.pxerstudio.BuildConfig
import com.benny.pxerstudio.R
import com.benny.pxerstudio.databinding.ActivityAboutBinding
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.licenses.MITLicense
import de.psdev.licensesdialog.model.Notice
import de.psdev.licensesdialog.model.Notices

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aboutAppVersion.text =
            "v" + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")"

        binding.aboutCreator.startIntent(this, "https://bennykok.com/")
        binding.aboutCreator.movementMethod = LinkMovementMethod.getInstance()

        binding.aboutMoreApps.startIntent(
            this,
            "https://play.google.com/store/apps/dev?id=9219874075463759288"
        )
        binding.aboutMoreApps.movementMethod = LinkMovementMethod.getInstance()

        binding.aboutGithub.startIntent(this, "https://github.com/BennyKok/PxerStudio")
        binding.aboutGithub.movementMethod = LinkMovementMethod.getInstance()

        val sb = StringBuilder()
        with(sb) {
            append(getString(R.string.brought_to_you_by))
            append("<br>")

            append("androidx.appcompat:appcompat")
            append("<br>")
            append("androidx.cardview:cardview")
            append("<br>")
            append("androidx.constraintlayout:constraintlayout")
            append("<br>")
            append("com.google.android.material:material")
            append("<br>")
            append("com.afollestad.material-dialogs:core")
            append("<br>")
            append("com.afollestad.material-dialogs:files")
            append("<br>")
            append("com.afollestad.material-dialogs:input")
            append("<br>")
            append("com.mikepenz:fastadapter")
            append("<br>")
            append("com.mikepenz:fastadapter-extensions")
            append("<br>")
            append("de.psdev.licensesdialog:licensesdialog")
            append("<br>")
            append("com.github.clans:fab")
            append("<br>")
            append("com.google.code.gson:gson")
            append("<br>")
        }

        binding.aboutLibinfo.movementMethod = LinkMovementMethod.getInstance()
        binding.aboutLibinfo.text = "$sb".parseAsHtml()

        binding.aboutAppIcon.setOnClickListener { v ->
            if (v.animation == null || v.animation != null && v.animation.hasEnded())
                v.animate()
                        .scaleX(1.1f)
                        .scaleY(1.1f)
                        .rotationBy(-20f)
                        .withEndAction {
                            v.animate()
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .rotation(0f)
                        }
        }

        setupLibDialog()
        setupContributorsDialog()
    }

    private fun setupContributorsDialog() {
        val notices = Notices()

        val n1 = Notice()
        n1.name = "BennyKok"
        n1.url = "https://github.com/BennyKok"
        notices.addNotice(n1)

        val n2 = Notice()
        n2.name = "TacoTheDank"
        n2.url = "https://github.com/TacoTheDank"
        notices.addNotice(n2)

        val n3 = Notice()
        n3.name = "mihajlo0743"
        n3.url = "https://github.com/mihajlo0743"
        notices.addNotice(n3)

        val n4 = Notice()
        n4.name = "factorial52"
        n4.url = "https://github.com/factorial52"
        notices.addNotice(n4)

        val builder = LicensesDialog.Builder(this@AboutActivity)
        builder.setNotices(notices)
        builder.setTitle(getString(R.string.opensource_contributors))
        val dialog = builder.build()

        binding.aboutCreator.setOnClickListener { dialog.show() }
    }

    private fun setupLibDialog() {
        val notices = Notices()
        notices.addNotice(
                Notice(
                        "Material Dialogs",
                        "https://github.com/afollestad/material-dialogs",
                        "Copyright (c) 2014-2016 Aidan Michael Follestad",
                        MITLicense()
                )
        )
        notices.addNotice(
                Notice(
                        "FastAdapter",
                        "https://github.com/mikepenz/FastAdapter",
                        "Copyright 2021 Mike Penz",
                        ApacheSoftwareLicense20()
                )
        )
        notices.addNotice(
                Notice(
                        "FloatingActionButton",
                        "https://github.com/Clans/FloatingActionButton",
                        "Copyright 2015 Dmytro Tarianyk",
                        ApacheSoftwareLicense20()
                )
        )
        notices.addNotice(
                Notice(
                        "Gson",
                        "https://github.com/google/gson",
                        "Copyright 2008 Google Inc.",
                        ApacheSoftwareLicense20()
                )
        )

        val builder = LicensesDialog.Builder(this@AboutActivity)
        builder.setIncludeOwnLicense(true)
        builder.setNotices(notices)
        builder.setTitle(getString(R.string.opensource_libraries))
        val dialog = builder.build()

        binding.aboutLibinfo.setOnClickListener { dialog.show() }
    }

    private fun TextView.startIntent(activity: Activity, uri: String) {
        setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, uri.toUri())
            activity.startActivity(i)
        }
    }
}
