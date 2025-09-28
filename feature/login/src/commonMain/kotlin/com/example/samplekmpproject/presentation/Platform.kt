package com.example.samplekmpproject.presentation


/**
 * Deklarasi expect:
 * - Di commonMain kita hanya "mengharapkan" implementasi fungsi tersedia di tiap platform.
 * - Implementasi konkretnya disediakan di androidMain/iosMain/desktopMain (actual).
 */
expect fun platformName(): String