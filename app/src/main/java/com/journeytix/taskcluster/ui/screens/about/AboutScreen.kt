package com.journeytix.taskcluster.ui.screens.about

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.BuildConfig
import com.journeytix.taskcluster.R
import com.journeytix.taskcluster.ui.components.core.Page
import com.journeytix.taskcluster.ui.components.core.PageGroup
import com.journeytix.taskcluster.ui.components.core.PageRow
import com.journeytix.taskcluster.ui.components.core.TaskButton
import com.journeytix.taskcluster.ui.components.core.TaskButtonSize
import com.journeytix.taskcluster.ui.components.core.TaskButtonVariant
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.screens.legal.LegalUrls
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Hairline
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink700
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.Space1
import com.journeytix.taskcluster.ui.theme.Space3
import com.journeytix.taskcluster.ui.theme.Space4
import com.journeytix.taskcluster.ui.theme.Space6

private const val PLAY_STORE_PACKAGE = "com.journeytix.taskcluster"
private const val SUPPORT_EMAIL = "support@journeytix.com"

@Composable
fun AboutScreen(
    onBack: () -> Unit,
    onOpenLegal: (title: String, url: String) -> Unit,
) {
    val context = LocalContext.current
    val versionName = BuildConfig.VERSION_NAME
    val versionCode = BuildConfig.VERSION_CODE

    Page(title = "About", onBack = onBack) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(Space4))
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_round),
                contentDescription = "TaskCluster icon",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.height(Space3))
            Text(
                text = "TaskCluster",
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp,
                ),
                color = Ink900,
            )
            Spacer(modifier = Modifier.height(Space1))
            Text(
                text = "v$versionName · Build $versionCode",
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 13.sp,
                ),
                color = Ink500,
            )
            Spacer(modifier = Modifier.height(Space1))
            Text(
                text = "A simple, focused task manager.",
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                ),
                color = Ink700,
            )
            Spacer(modifier = Modifier.height(Space4))
            Text(
                text = "Made by Journeytix",
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 13.sp,
                ),
                color = Ink500,
            )
            Spacer(modifier = Modifier.height(Space6))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Hairline),
        )
        Spacer(modifier = Modifier.height(Space6))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TaskButton(
                text = "Rate",
                onClick = { openPlayStore(context) },
                variant = TaskButtonVariant.Secondary,
                size = TaskButtonSize.Md,
                iconLeft = TaskIcons.Star,
                modifier = Modifier.weight(1f),
            )
            TaskButton(
                text = "Share",
                onClick = { shareApp(context) },
                variant = TaskButtonVariant.Secondary,
                size = TaskButtonSize.Md,
                iconLeft = TaskIcons.Share,
                modifier = Modifier.weight(1f),
            )
            TaskButton(
                text = "Feedback",
                onClick = { sendFeedback(context, versionName, versionCode) },
                variant = TaskButtonVariant.Secondary,
                size = TaskButtonSize.Md,
                iconLeft = TaskIcons.Mail,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(Space6))

        PageGroup(title = "Legal") {
            PageRow(
                title = "Privacy Policy",
                onClick = { onOpenLegal("Privacy Policy", LegalUrls.PRIVACY_POLICY) },
            )
            PageRow(
                title = "Terms of Service",
                onClick = { onOpenLegal("Terms of Service", LegalUrls.TERMS_OF_SERVICE) },
            )
            PageRow(
                title = "End User License Agreement",
                onClick = { onOpenLegal("End User License Agreement", LegalUrls.EULA) },
            )
            PageRow(
                title = "Data Deletion Policy",
                onClick = { onOpenLegal("Data Deletion Policy", LegalUrls.DATA_DELETION) },
                last = true,
            )
        }

        PageGroup(title = "Open source") {
            PageRow(
                title = "Open source licenses",
                onClick = { openOssLicenses(context) },
                last = true,
            )
        }
    }
}

private fun openPlayStore(context: Context) {
    val uri = Uri.parse("market://details?id=$PLAY_STORE_PACKAGE")
    val webUri = Uri.parse("https://play.google.com/store/apps/details?id=$PLAY_STORE_PACKAGE")
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    } catch (_: ActivityNotFoundException) {
        context.startActivity(Intent(Intent.ACTION_VIEW, webUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}

private fun shareApp(context: Context) {
    val shareText = "Check out TaskCluster — a simple, focused task manager. " +
        "https://play.google.com/store/apps/details?id=$PLAY_STORE_PACKAGE"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(Intent.createChooser(intent, null).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}

private fun sendFeedback(context: Context, versionName: String, versionCode: Int) {
    val deviceInfo = """
        |
        |---
        |Device: ${Build.MANUFACTURER} ${Build.MODEL}
        |Android: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})
        |App: v$versionName (Build $versionCode)
    """.trimMargin()

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(SUPPORT_EMAIL))
        putExtra(Intent.EXTRA_SUBJECT, "TaskCluster Feedback — v$versionName")
        putExtra(Intent.EXTRA_TEXT, deviceInfo)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
    }
}

private fun openOssLicenses(context: Context) {
    try {
        val activityClass = Class.forName(
            "com.google.android.gms.oss.licenses.OssLicensesMenuActivity"
        )
        val intent = Intent(context, activityClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (_: ClassNotFoundException) {
        Toast.makeText(context, "Licenses not available", Toast.LENGTH_SHORT).show()
    }
}
