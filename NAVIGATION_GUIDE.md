# Sample Navigation dengan Voyager

## Flow Navigation
```
LoginScreen → HomeScreen → AccountScreen
```

## Implementasi yang Telah Dibuat

### 1. LoginScreen (Sudah ada)
- **Lokasi**: `feature/login/src/commonMain/kotlin/com/example/samplekmpproject/presentation/LoginScreen.kt`
- **Fungsi**: Entry point aplikasi, menerima username dan password
- **Navigation**: Setelah login berhasil, navigate ke `HomeScreen(username)`

### 2. HomeScreen (Dimodifikasi)
- **Lokasi**: `feature/home/src/commonMain/kotlin/com/example/samplekmpproject/presentation/HomeScreen.kt`
- **Fitur Baru**: 
  - Menampilkan tombol "Account" di header
  - Klik tombol Account akan navigate ke `AccountScreen(username)`
- **Navigation**: `navigator.push(AccountScreen(username))`

### 3. AccountScreen (Dibuat Baru)
- **Lokasi**: `feature/account/src/commonMain/kotlin/com/example/account/AccountScreen.kt`
- **Fitur**:
  - Menampilkan informasi akun user
  - Tombol "Back to Home" → `navigator.pop()`
  - Tombol "Logout" → `navigator.popAll()` (kembali ke LoginScreen)

## Cara Kerja Navigation

### A. LoginScreen ke HomeScreen
```kotlin
LoginScreen { navigator, username ->
    navigator.push(HomeScreen(username))
}
```

### B. HomeScreen ke AccountScreen
```kotlin
Button(
    onClick = { 
        navigator.push(AccountScreen(username))
    }
) {
    Text("Account")
}
```

### C. AccountScreen ke HomeScreen (Back)
```kotlin
Button(
    onClick = { navigator.pop() }
) {
    Text("Back to Home")
}
```

### D. AccountScreen ke LoginScreen (Logout)
```kotlin
Button(
    onClick = { navigator.popAll() }
) {
    Text("Logout")
}
```

## Entry Point Aplikasi
**File**: `composeApp/src/commonMain/kotlin/com/example/samplekmpproject/App.kt`

```kotlin
@Composable
fun App() {
    MaterialTheme {
        Navigator(
            screen = LoginScreen { navigator, username ->
                navigator.push(HomeScreen(username))
            }
        )
    }
}
```

## Dependencies yang Ditambahkan

### feature/home/build.gradle.kts
```kotlin
implementation(project(":feature:account"))
```

## Best Practices yang Digunakan

### 1. **Screen Sebagai Data Class**
```kotlin
data class HomeScreen(val username: String) : Screen
data class AccountScreen(val username: String) : Screen
```
- Memungkinkan passing parameter antar screen
- Type-safe navigation

### 2. **Consistent Navigation Pattern**
- `navigator.push()` - untuk pindah ke screen baru
- `navigator.pop()` - untuk kembali ke screen sebelumnya
- `navigator.popAll()` - untuk kembali ke root screen

### 3. **Parameter Passing**
- Username dibawa dari LoginScreen → HomeScreen → AccountScreen
- Memastikan konteks user tetap terjaga di semua screen

### 4. **Modular Architecture**
- Setiap feature memiliki module terpisah
- Clean separation of concerns
- Mudah untuk maintenance dan testing

## Cara Menjalankan
1. Build aplikasi: `./gradlew build`
2. Jalankan aplikasi 
3. Flow navigation:
   - Masukkan username/password di LoginScreen
   - Klik "Login" → akan pindah ke HomeScreen
   - Klik tombol "Account" di HomeScreen → pindah ke AccountScreen
   - Di AccountScreen: klik "Back to Home" atau "Logout"

## Keunggulan Implementasi Ini
- ✅ **Type-safe navigation** dengan parameter yang jelas
- ✅ **Multiplatform support** (Android, iOS, Desktop)
- ✅ **Clean architecture** dengan separation of concerns
- ✅ **Reusable screens** yang bisa digunakan di berbagai flow
- ✅ **Consistent UX** dengan navigation pattern yang standar
