package eu.kanade.tachiyomi.ui.reader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.kanade.presentation.components.AdaptiveSheet
import eu.kanade.tachiyomi.R
import tachiyomi.presentation.core.components.ActionButton
import tachiyomi.presentation.core.components.material.padding

@Composable
fun ReaderPageDialog(
    onDismissRequest: () -> Unit,
    // SY -->
    onSetAsCover: (useExtraPage: Boolean) -> Unit,
    onShare: (useExtraPage: Boolean) -> Unit,
    onSave: (useExtraPage: Boolean) -> Unit,
    onShareCombined: () -> Unit,
    onSaveCombined: () -> Unit,
    hasExtraPage: Boolean,
    // SY <--
) {
    var showSetCoverDialog by remember { mutableStateOf(false) }
    // SY -->
    var useExtraPage by remember { mutableStateOf(false) }
    // SY <--

    AdaptiveSheet(
        onDismissRequest = onDismissRequest,
    ) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            ) {
                ActionButton(
                    modifier = Modifier.weight(1f),
                    title = stringResource(
                        // SY -->
                        if (hasExtraPage) {
                            R.string.action_set_first_page_cover
                        } else {
                            R.string.set_as_cover
                        },
                        // SY <--
                    ),
                    icon = Icons.Outlined.Photo,
                    onClick = { showSetCoverDialog = true },
                )
                ActionButton(
                    modifier = Modifier.weight(1f),
                    title = stringResource(
                        // SY -->
                        if (hasExtraPage) {
                            R.string.action_share_first_page
                        } else {
                            R.string.action_share
                        },
                        // SY <--
                    ),
                    icon = Icons.Outlined.Share,
                    onClick = {
                        // SY -->
                        onShare(false)
                        // SY <--
                        onDismissRequest()
                    },
                )

                ActionButton(
                    modifier = Modifier.weight(1f),
                    title = stringResource(
                        // SY -->
                        if (hasExtraPage) {
                            R.string.action_save_first_page
                        } else {
                            R.string.action_save
                        },
                        // SY <--
                    ),
                    icon = Icons.Outlined.Save,
                    onClick = {
                        // SY -->
                        onSave(false)
                        // SY <--
                        onDismissRequest()
                    },
                )
            }
            if (hasExtraPage) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
                ) {
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.action_set_second_page_cover),
                        icon = Icons.Outlined.Photo,
                        onClick = {
                            showSetCoverDialog = true
                        },
                    )
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.action_share_second_page),
                        icon = Icons.Outlined.Share,
                        onClick = {
                            onShare(true)
                            onDismissRequest()
                        },
                    )
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.action_save_second_page),
                        icon = Icons.Outlined.Save,
                        onClick = {
                            onSave(true)
                            onDismissRequest()
                        },
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
                ) {
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.action_share_combined_page),
                        icon = Icons.Outlined.Share,
                        onClick = {
                            onShareCombined()
                            onDismissRequest()
                        },
                    )
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.action_save_combined_page),
                        icon = Icons.Outlined.Save,
                        onClick = {
                            onSaveCombined()
                            onDismissRequest()
                        },
                    )
                }
            }
        }
    }

    if (showSetCoverDialog) {
        SetCoverDialog(
            onConfirm = {
                // SY -->
                onSetAsCover(useExtraPage)
                showSetCoverDialog = false
                useExtraPage = false
                // SY <--
            },
            onDismiss = { showSetCoverDialog = false },
        )
    }
}

@Composable
private fun SetCoverDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        text = {
            Text(stringResource(R.string.confirm_set_image_as_cover))
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.action_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.action_cancel))
            }
        },
        onDismissRequest = onDismiss,
    )
}
