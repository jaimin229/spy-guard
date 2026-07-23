# Privacy Policy for Spy Guard

**Hosted URL:** [https://jaimin229.github.io/spy-guard/](https://jaimin229.github.io/spy-guard/)  
**Last Updated:** July 23, 2026

**Spy Guard** ("we", "our", or "us") is committed to protecting your privacy. This Privacy Policy explains how our mobile application **Spy Guard** (`com.spyguard.security`) operates and handles user information.

---

## 1. 100% Offline & Local Data Processing

Spy Guard is designed with a **Strict Local-First Privacy Architecture**. 

- **No Remote Servers:** We do not own, operate, or connect to any cloud servers, databases, or remote services (no Firebase, no Supabase, no custom backend).
- **Zero Internet Usage:** Spy Guard does not request or require the `android.permission.INTERNET` permission. The application cannot communicate over the internet or send data off your device.
- **All Data Stored Locally:** All app settings, locked app package names, unlock event logs, and intruder selfie photographs remain stored exclusively on your device's internal encrypted storage.

---

## 2. Information Handled on Device

Spy Guard processes the following types of information strictly on your local device:

1. **Master Passcode / Pattern Hashes:** Encrypted locally using the **Android KeyStore AES-256 GCM** encryption system.
2. **Locked Application Packages:** Package names of applications selected by you for lock protection.
3. **Intruder Selfie Photos:** Front-camera photographs captured quietly during unauthorized access attempts (stored in your device's private app storage folder at `context.filesDir/intruders/`).
4. **Security Logs:** Timestamps, application names, and success/failure attempt statuses recorded locally.

---

## 3. Permissions Used & Rationale

Spy Guard requests the following system permissions to perform its privacy features:

- **Accessibility Service (`BIND_ACCESSIBILITY_SERVICE`):** Used solely to detect when a locked application is launched by the user so that the security authentication screen can be displayed. Spy Guard does NOT read screen content, text, passwords, or personal messages.
- **Display Over Other Apps (`SYSTEM_ALERT_WINDOW`):** Allows Spy Guard to draw the security PIN/Pattern overlay above locked apps.
- **Camera (`CAMERA`):** Used exclusively to capture a front-camera photograph when an incorrect PIN or pattern is entered multiple times. Photos are saved locally and never shared.
- **Usage Access Stats (`PACKAGE_USAGE_STATS`):** Used to identify active foreground apps for protection.
- **Notifications (`POST_NOTIFICATIONS`):** Used to display offline security alert notifications when unauthorized access attempts occur.
- **Biometric (`USE_BIOMETRIC`):** Used to allow local fingerprint or face authentication via Android's native `BiometricPrompt` framework.

---

## 4. Third-Party Services & Ad Networks

Spy Guard displays advertisements powered by **Google AdMob**. AdMob may collect and process certain non-personal device information (such as Google Advertising ID, device model, and coarse location) to serve non-personalized or personalized ads in compliance with Google Play Developer Policies.

For more information, please review [Google Privacy Policy](https://policies.google.com/privacy).

---

## 5. Children's Privacy (COPPA & Global Codes)

Spy Guard does not knowingly collect or solicit any personal data from anyone, including children under the age of 13 (or under 18 in relevant jurisdictions). Since no data ever leaves your device, no data from children can be collected or transmitted.

---

## 6. Data Retention & Deletion

Since all data resides on your device:
- You can delete intruder selfie photos individually or in bulk inside the **Intruder Selfies** screen.
- You can clear all security logs from the **App Lock Logs** screen.
- Uninstalling the Spy Guard application from your Android device will permanently remove all local databases, settings, and captured intruder selfies immediately.

---

## 7. Contact Us

If you have any questions or concerns regarding this Privacy Policy or Spy Guard's offline security design, please contact us at:

- **Developer / Entity Name:** Spy Guard Security Team
- **Support Email:** jaiminadac@gmail.com
