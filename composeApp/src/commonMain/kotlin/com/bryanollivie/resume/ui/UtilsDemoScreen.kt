package com.bryanollivie.resume.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.bryanollivie.resume.data.AppLanguage
import com.bryanollivie.resume.data.LocalLanguage
import com.bryanollivie.resume.designsystem.components.SectionCard
import com.bryanollivie.resume.designsystem.tokens.ResumeColors
import com.bryanollivie.resume.designsystem.tokens.Spacing
import com.bryanollivie.kmputils.*
import androidx.compose.ui.unit.sp

@Composable
private fun Desc(en: String, pt: String) {
    val lang = LocalLanguage.current.value
    Text(
        text = if (lang == AppLanguage.PT) pt else en,
        style = MaterialTheme.typography.bodySmall,
        color = ResumeColors.SecondaryText,
        lineHeight = 16.sp
    )
    Spacer(modifier = Modifier.height(Spacing.dp8))
}

@Composable
fun UtilsDemoScreen() {
    val lang = LocalLanguage.current.value

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = Spacing.dp6, end = Spacing.dp6, top = Spacing.dp16, bottom = 56.dp),
        verticalArrangement = Arrangement.spacedBy(Spacing.dp8)
    ) {
        item { CurrencySection() }
        item { MasksSection() }
        item { ValidatorsSection() }
        item { CreditCardSection() }
        item { CryptoSection() }
        item { ColorSection() }
        item { DeviceInfoSection() }
        item { NetworkSection(lang) }
        item { ClipboardSection(lang) }
        item { DateSection(lang) }
        item { StringSection() }
        item { CacheSection() }
        item { RateLimiterSection() }
        item { StateMachineSection() }
        item { FormValidationSection() }
        item { DebounceSection() }
        item { RetrySection() }
        item { EventBusSection() }
        item { HapticSection(lang) }
    }
}

@Composable
private fun CurrencySection() {
    var amount by remember { mutableStateOf("1000") }
    var selectedCurrency by remember { mutableStateOf(CurrencyCode.BRL) }

    SectionCard(title = "Currency", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Format and convert currencies (BRL, USD, EUR) with locale-aware formatting.", "Formata e converte moedas (BRL, USD, EUR) com formatação regional.")
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it.filter { c -> c.isDigit() || c == '.' } },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp8))

        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.dp6)) {
            CurrencyCode.entries.forEach { currency ->
                FilterChip(
                    selected = selectedCurrency == currency,
                    onClick = { selectedCurrency = currency },
                    label = { Text(currency.name) }
                )
            }
        }
        Spacer(modifier = Modifier.height(Spacing.dp8))

        val value = amount.toDoubleOrNull() ?: 0.0
        Text(
            text = CurrencyUtils.format(value, selectedCurrency),
            style = MaterialTheme.typography.headlineSmall,
            color = ResumeColors.Primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(Spacing.dp4))

        CurrencyCode.entries.filter { it != selectedCurrency }.forEach { target ->
            val converted = CurrencyUtils.convert(value, selectedCurrency, target)
            Text(
                text = "→ ${CurrencyUtils.format(converted, target)}",
                style = MaterialTheme.typography.bodyMedium,
                color = ResumeColors.SecondaryText
            )
        }
    }
}

@Composable
private fun MasksSection() {
    var input by remember { mutableStateOf("") }
    var selectedMask by remember { mutableStateOf("Phone") }
    val masks = listOf("Phone", "CPF", "CNPJ", "Card", "Date", "CEP")

    SectionCard(title = "Masks", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Apply input masks for Phone, CPF, CNPJ, Credit Card, Date, and CEP.", "Aplica máscaras de entrada para Telefone, CPF, CNPJ, Cartão, Data e CEP.")
        OutlinedTextField(
            value = input,
            onValueChange = { input = it.filter { c -> c.isDigit() } },
            label = { Text("Type numbers") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp8))

        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.dp4)) {
            masks.take(3).forEach { mask ->
                FilterChip(
                    selected = selectedMask == mask,
                    onClick = { selectedMask = mask; input = "" },
                    label = { Text(mask) }
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.dp4)) {
            masks.drop(3).forEach { mask ->
                FilterChip(
                    selected = selectedMask == mask,
                    onClick = { selectedMask = mask; input = "" },
                    label = { Text(mask) }
                )
            }
        }
        Spacer(modifier = Modifier.height(Spacing.dp8))

        val masked = when (selectedMask) {
            "Phone" -> MaskUtils.maskPhone(input)
            "CPF" -> MaskUtils.maskCpf(input)
            "CNPJ" -> MaskUtils.maskCnpj(input)
            "Card" -> MaskUtils.maskCreditCard(input)
            "Date" -> MaskUtils.maskDate(input)
            "CEP" -> MaskUtils.maskCep(input)
            else -> input
        }
        Text(
            text = masked.ifEmpty { "---" },
            style = MaterialTheme.typography.headlineSmall,
            color = ResumeColors.Primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ValidatorsSection() {
    var input by remember { mutableStateOf("") }
    var selectedValidator by remember { mutableStateOf("Email") }
    val validators = listOf("Email", "Phone", "CPF", "CNPJ")

    SectionCard(title = "Validators", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Validate Email, Phone, CPF (with check digit), and CNPJ formats.", "Valida Email, Telefone, CPF (com dígito verificador) e CNPJ.")
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text(selectedValidator) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp8))

        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.dp4)) {
            validators.forEach { v ->
                FilterChip(
                    selected = selectedValidator == v,
                    onClick = { selectedValidator = v; input = "" },
                    label = { Text(v) }
                )
            }
        }
        Spacer(modifier = Modifier.height(Spacing.dp8))

        if (input.isNotEmpty()) {
            val isValid = when (selectedValidator) {
                "Email" -> Validators.isValidEmail(input)
                "Phone" -> Validators.isValidPhone(input)
                "CPF" -> Validators.isValidCpf(input)
                "CNPJ" -> Validators.isValidCnpj(input)
                else -> false
            }
            Text(
                text = if (isValid) "Valid ✓" else "Invalid ✗",
                style = MaterialTheme.typography.titleLarge,
                color = if (isValid) ResumeColors.Accent else ResumeColors.Primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun NetworkSection(lang: AppLanguage) {
    var result by remember { mutableStateOf<Boolean?>(null) }

    SectionCard(title = "Network", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Check internet connectivity using platform-native APIs (ConnectivityManager / NWPathMonitor / navigator.onLine).", "Verifica conectividade usando APIs nativas da plataforma (ConnectivityManager / NWPathMonitor / navigator.onLine).")
        Button(
            onClick = { result = isNetworkAvailable() },
            colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Primary)
        ) {
            Text(if (lang == AppLanguage.PT) "Verificar Conexão" else "Check Connection")
        }
        Spacer(modifier = Modifier.height(Spacing.dp8))
        result?.let {
            Text(
                text = if (it) {
                    if (lang == AppLanguage.PT) "Conectado ✓" else "Connected ✓"
                } else {
                    if (lang == AppLanguage.PT) "Sem conexão ✗" else "No connection ✗"
                },
                style = MaterialTheme.typography.titleLarge,
                color = if (it) ResumeColors.Accent else ResumeColors.Primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ClipboardSection(lang: AppLanguage) {
    var text by remember { mutableStateOf("") }
    var copied by remember { mutableStateOf(false) }

    SectionCard(title = "Clipboard", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Copy text to system clipboard using platform-native APIs.", "Copia texto para a área de transferência usando APIs nativas da plataforma.")
        OutlinedTextField(
            value = text,
            onValueChange = { text = it; copied = false },
            label = { Text(if (lang == AppLanguage.PT) "Texto para copiar" else "Text to copy") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp8))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    copyToClipboard(text)
                    copied = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Primary)
            ) {
                Text(if (lang == AppLanguage.PT) "Copiar" else "Copy")
            }
            if (copied) {
                Spacer(modifier = Modifier.width(Spacing.dp12))
                Text(
                    text = if (lang == AppLanguage.PT) "Copiado! ✓" else "Copied! ✓",
                    color = ResumeColors.Accent,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun DateSection(lang: AppLanguage) {
    val now = DateUtils.now()

    SectionCard(title = "Date Utils", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Format dates in multiple patterns and display relative time (e.g. '2 hours ago').", "Formata datas em vários padrões e exibe tempo relativo (ex: 'há 2 horas').")
        val patterns = listOf("dd/MM/yyyy", "MM-dd-yyyy", "yyyy-MM-dd", "dd/MM/yyyy HH:mm:ss")
        patterns.forEach { pattern ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(pattern, style = MaterialTheme.typography.bodySmall, color = ResumeColors.SecondaryText)
                Text(
                    DateUtils.formatDate(now, pattern),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(Spacing.dp4))
        }

        Spacer(modifier = Modifier.height(Spacing.dp8))
        Text(
            text = "Relative Time",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(Spacing.dp4))

        val langStr = if (lang == AppLanguage.PT) "pt" else "en"
        val examples = listOf(
            now - 30_000L to "30s",
            now - 300_000L to "5min",
            now - 7_200_000L to "2h",
            now - 172_800_000L to "2d"
        )
        examples.forEach { (millis, label) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(label, style = MaterialTheme.typography.bodySmall, color = ResumeColors.SecondaryText)
                Text(
                    DateUtils.relativeTime(millis, now, langStr),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun StringSection() {
    var input by remember { mutableStateOf("são paulo é incrível") }

    SectionCard(title = "String Utils", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Capitalize words, truncate text, remove accents, and generate URL slugs.", "Capitaliza palavras, trunca texto, remove acentos e gera slugs para URLs.")
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Input text") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp8))

        val results = listOf(
            "Capitalize" to StringUtils.capitalize(input),
            "Truncate(15)" to StringUtils.truncate(input, 15),
            "Remove Accents" to StringUtils.removeAccents(input),
            "Slug" to StringUtils.toSlug(input)
        )
        results.forEach { (label, result) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(label, style = MaterialTheme.typography.bodySmall, color = ResumeColors.SecondaryText)
                Text(
                    result,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(Spacing.dp4))
        }
    }
}

@Composable
private fun CreditCardSection() {
    var number by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var expMonth by remember { mutableStateOf("") }
    var expYear by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("1000") }

    SectionCard(title = "Credit Card", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Detect card brand (Visa, Mastercard, Elo...), Luhn validation, CVV, expiry check, and installment calculator.", "Detecta bandeira (Visa, Mastercard, Elo...), validação Luhn, CVV, validade e calculadora de parcelas.")
        OutlinedTextField(
            value = number,
            onValueChange = { number = it.filter { c -> c.isDigit() }.take(19) },
            label = { Text("Card Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp4))

        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.dp8)) {
            OutlinedTextField(
                value = expMonth,
                onValueChange = { expMonth = it.filter { c -> c.isDigit() }.take(2) },
                label = { Text("MM") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = expYear,
                onValueChange = { expYear = it.filter { c -> c.isDigit() }.take(2) },
                label = { Text("YY") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it.filter { c -> c.isDigit() }.take(4) },
                label = { Text("CVV") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(Spacing.dp8))

        if (number.isNotEmpty()) {
            val brand = CreditCardUtils.detectBrand(number)
            val isValid = CreditCardUtils.isValidNumber(number)
            val masked = CreditCardUtils.maskNumber(number)
            val formatted = CreditCardUtils.formatForDisplay(number)

            Text(
                text = "Brand: ${brand.displayName}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (brand != CardBrand.UNKNOWN) ResumeColors.Primary else ResumeColors.SecondaryText
            )
            Text("Formatted: $formatted", style = MaterialTheme.typography.bodyMedium)
            Text("Masked: $masked", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = if (isValid) "Luhn: Valid ✓" else "Luhn: Invalid ✗",
                color = if (isValid) ResumeColors.Accent else ResumeColors.Primary,
                fontWeight = FontWeight.Bold
            )

            if (cvv.isNotEmpty()) {
                val cvvValid = CreditCardUtils.isValidCvv(cvv, brand)
                Text(
                    text = if (cvvValid) "CVV: Valid ✓" else "CVV: Invalid (need ${brand.cvvLength} digits)",
                    color = if (cvvValid) ResumeColors.Accent else ResumeColors.Primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            val month = expMonth.toIntOrNull()
            val year = expYear.toIntOrNull()
            if (month != null && year != null) {
                val expiryValid = CreditCardUtils.isValidExpiry(month, year)
                Text(
                    text = if (expiryValid) "Expiry: Valid ✓" else "Expiry: Expired ✗",
                    color = if (expiryValid) ResumeColors.Accent else ResumeColors.Primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.dp8))
        HorizontalDivider(color = ResumeColors.Divider)
        Spacer(modifier = Modifier.height(Spacing.dp8))

        // Installments
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it.filter { c -> c.isDigit() || c == '.' } },
            label = { Text("Amount for installments") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp6))

        val totalAmount = amount.toDoubleOrNull() ?: 0.0
        if (totalAmount > 0) {
            val options = CreditCardUtils.getInstallmentOptions(totalAmount, maxInstallments = 6)
            Text("Installments:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(Spacing.dp4))
            options.forEach { opt ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${opt.installments}x",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        CurrencyUtils.format(opt.installmentValue, CurrencyCode.BRL),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun CryptoSection() {
    var input by remember { mutableStateOf("Hello KMP!") }
    var key by remember { mutableStateOf("secret") }

    SectionCard(title = "Encryption", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("XOR encrypt/decrypt, FNV hash, Base64 encoding, and random token generation.", "Criptografia XOR, hash FNV, codificação Base64 e geração de tokens aleatórios.")
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Text") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp4))
        OutlinedTextField(
            value = key,
            onValueChange = { key = it },
            label = { Text("Key") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp8))

        if (input.isNotEmpty() && key.isNotEmpty()) {
            val encrypted = CryptoUtils.encrypt(input, key)
            val decrypted = CryptoUtils.decrypt(encrypted, key)
            val hash = CryptoUtils.hash(input)
            val token = remember { CryptoUtils.generateToken(16) }
            val base64 = CryptoUtils.base64Encode(input)

            listOf(
                "Encrypted" to encrypted,
                "Decrypted" to decrypted,
                "Hash (FNV)" to hash,
                "Token" to token,
                "Base64" to base64
            ).forEach { (label, value) ->
                Text(label, style = MaterialTheme.typography.bodySmall, color = ResumeColors.SecondaryText)
                Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(Spacing.dp4))
            }
        }
    }
}

@Composable
private fun ColorSection() {
    var hex by remember { mutableStateOf("#D32F2F") }

    SectionCard(title = "Color Utils", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Convert Hex to RGB, darken/lighten colors, check contrast ratio and WCAG accessibility.", "Converte Hex para RGB, escurece/clareia cores, verifica contraste e acessibilidade WCAG.")
        OutlinedTextField(
            value = hex,
            onValueChange = { hex = it },
            label = { Text("Hex Color") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp8))

        val clean = hex.removePrefix("#")
        val isValidHex = (clean.length == 6 || clean.length == 8) && clean.all { it in "0123456789ABCDEFabcdef" }

        if (isValidHex) {
            val color = ColorUtils.hexToColor(hex)
            val darkened = ColorUtils.darken(color, 0.3f)
            val lightened = ColorUtils.lighten(color, 0.3f)
            val contrast = ColorUtils.contrastRatio(color, Color.White)
            val accessible = ColorUtils.isAccessible(color, Color.White)

            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.dp8),
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("Original" to color, "Dark" to darkened, "Light" to lightened).forEach { (label, c) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(c)
                        )
                        Text(label, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Spacer(modifier = Modifier.height(Spacing.dp8))
            Text("RGB: ${ColorUtils.toRgbString(color)}", style = MaterialTheme.typography.bodyMedium)
            Text("Contrast vs White: ${"%.2f".format(contrast)}:1", style = MaterialTheme.typography.bodyMedium)
            Text(
                "WCAG AA: ${if (accessible) "Pass" else "Fail"}",
                style = MaterialTheme.typography.bodyMedium,
                color = if (accessible) ResumeColors.Accent else ResumeColors.Primary,
                fontWeight = FontWeight.Bold
            )
        } else if (clean.isNotEmpty()) {
            Text("Invalid hex", color = ResumeColors.Primary)
        }
    }
}

@Composable
private fun DeviceInfoSection() {
    val info = remember { getDeviceInfo() }
    val darkMode = isDarkMode()

    SectionCard(title = "Device Info", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Get device model, OS name/version, and dark mode status via expect/actual.", "Obtém modelo do dispositivo, nome/versão do SO e status do modo escuro via expect/actual.")
        listOf(
            "Model" to info.model,
            "OS" to info.osName,
            "Version" to info.osVersion,
            "Dark Mode" to if (darkMode) "Yes" else "No"
        ).forEach { (label, value) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(label, style = MaterialTheme.typography.bodySmall, color = ResumeColors.SecondaryText)
                Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(Spacing.dp4))
        }
    }
}

@Composable
private fun CacheSection() {
    val cache = remember { LruCache<String, String>(5) }
    var key by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var cacheState by remember { mutableStateOf(0) }

    SectionCard(title = "LRU Cache (max 5)", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("In-memory cache with Least Recently Used eviction. Oldest entries are removed when capacity is exceeded.", "Cache em memória com remoção por Menos Recentemente Usado. Entradas mais antigas são removidas ao exceder a capacidade.")
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.dp4)) {
            OutlinedTextField(
                value = key,
                onValueChange = { key = it },
                label = { Text("Key") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text("Value") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(Spacing.dp6))
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.dp6)) {
            Button(
                onClick = {
                    if (key.isNotEmpty()) {
                        cache.put(key, value)
                        key = ""; value = ""; cacheState++
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Primary)
            ) { Text("Put") }
            OutlinedButton(onClick = { cache.clear(); cacheState++ }) { Text("Clear") }
        }
        Spacer(modifier = Modifier.height(Spacing.dp6))
        // Force recomposition on cacheState
        cacheState.let {
            Text("Size: ${cache.size}/5", style = MaterialTheme.typography.bodySmall, color = ResumeColors.SecondaryText)
            cache.snapshot().forEach { (k, v) ->
                Text("$k → $v", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun RateLimiterSection() {
    val limiter = remember { RateLimiter(maxCalls = 3, windowMs = 5000L) }
    var clicks by remember { mutableStateOf(0) }
    var blocked by remember { mutableStateOf(0) }

    SectionCard(title = "Rate Limiter (3/5s)", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Limits function calls to a maximum rate. Here: 3 calls per 5-second window.", "Limita chamadas a uma taxa máxima. Aqui: 3 chamadas por janela de 5 segundos.")
        Button(
            onClick = {
                if (limiter.tryAcquire()) clicks++ else blocked++
            },
            colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Primary)
        ) { Text("Click me fast!") }
        Spacer(modifier = Modifier.height(Spacing.dp6))
        Text("Accepted: $clicks", color = ResumeColors.Accent, fontWeight = FontWeight.Bold)
        Text("Blocked: $blocked", color = ResumeColors.Primary, fontWeight = FontWeight.Bold)
        Text("Remaining: ${limiter.remainingCalls()}", style = MaterialTheme.typography.bodySmall, color = ResumeColors.SecondaryText)
    }
}

@Composable
private fun StateMachineSection() {
    val machine = remember {
        stateMachine<String, String>("Idle") {
            transition("Idle", "START", "Loading")
            transition("Loading", "SUCCESS", "Success")
            transition("Loading", "ERROR", "Error")
            transition("Success", "RESET", "Idle")
            transition("Error", "RETRY", "Loading")
            transition("Error", "RESET", "Idle")
        }
    }
    val state by machine.state.collectAsState()

    SectionCard(title = "State Machine", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Generic finite state machine with typed transitions and history tracking.", "Máquina de estados finitos genérica com transições tipadas e rastreamento de histórico.")
        Text(
            text = state,
            style = MaterialTheme.typography.headlineSmall,
            color = when (state) {
                "Success" -> ResumeColors.Accent
                "Error" -> ResumeColors.Primary
                "Loading" -> Color(0xFFFF9800)
                else -> ResumeColors.PrimaryDark
            },
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(Spacing.dp6))
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.dp4)) {
            when (state) {
                "Idle" -> {
                    Button(onClick = { machine.send("START") },
                        colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Primary)
                    ) { Text("Start") }
                }
                "Loading" -> {
                    Button(onClick = { machine.send("SUCCESS") },
                        colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Accent)
                    ) { Text("Success") }
                    Button(onClick = { machine.send("ERROR") },
                        colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Primary)
                    ) { Text("Error") }
                }
                "Success" -> {
                    Button(onClick = { machine.send("RESET") },
                        colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.PrimaryDark)
                    ) { Text("Reset") }
                }
                "Error" -> {
                    Button(onClick = { machine.send("RETRY") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                    ) { Text("Retry") }
                    Button(onClick = { machine.send("RESET") },
                        colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.PrimaryDark)
                    ) { Text("Reset") }
                }
            }
        }
        Spacer(modifier = Modifier.height(Spacing.dp4))
        Text(
            "History: ${machine.history.joinToString(" → ")}",
            style = MaterialTheme.typography.bodySmall,
            color = ResumeColors.SecondaryText
        )
    }
}

@Composable
private fun FormValidationSection() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }

    val validator = remember {
        FormValidator()
            .addField("name", FormRules.required(), FormRules.minLength(3))
            .addField("email", FormRules.required(), FormRules.email())
            .addField("cpf", FormRules.required(), FormRules.cpf())
    }

    SectionCard(title = "Form Validation", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Composable validation engine with chainable rules (required, minLength, email, CPF).", "Engine de validação com regras encadeáveis (obrigatório, tamanho mínimo, email, CPF).")
        val nameResult = if (name.isNotEmpty()) validator.validateField("name", name) else null
        val emailResult = if (email.isNotEmpty()) validator.validateField("email", email) else null
        val cpfResult = if (cpf.isNotEmpty()) validator.validateField("cpf", cpf) else null

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name *") },
            isError = nameResult?.isValid == false,
            supportingText = { nameResult?.errorMessage?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email *") },
            isError = emailResult?.isValid == false,
            supportingText = { emailResult?.errorMessage?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF *") },
            isError = cpfResult?.isValid == false,
            supportingText = { cpfResult?.errorMessage?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        val allValid = validator.isFormValid(mapOf("name" to name, "email" to email, "cpf" to cpf))
        Spacer(modifier = Modifier.height(Spacing.dp4))
        Text(
            text = if (allValid) "Form is valid ✓" else "Fill all fields correctly",
            color = if (allValid) ResumeColors.Accent else ResumeColors.SecondaryText,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DebounceSection() {
    var input by remember { mutableStateOf("") }
    var debouncedValue by remember { mutableStateOf("") }
    var typingCount by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    val debounced = remember {
        DebounceThrottle.debounceLatest<String>(500L, scope) { value ->
            debouncedValue = value
        }
    }

    SectionCard(title = "Debounce (500ms)", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Delays execution until user stops typing for 500ms. Ideal for search inputs.", "Atrasa a execução até o usuário parar de digitar por 500ms. Ideal para campos de busca.")
        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                typingCount++
                debounced(it)
            },
            label = { Text("Type fast...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(Spacing.dp8))
        Text("Keystrokes: $typingCount", style = MaterialTheme.typography.bodySmall, color = ResumeColors.SecondaryText)
        Text(
            text = "Debounced: ${debouncedValue.ifEmpty { "---" }}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = ResumeColors.Primary
        )
        Spacer(modifier = Modifier.height(Spacing.dp4))
        Text(
            "Value updates only after 500ms of inactivity",
            style = MaterialTheme.typography.bodySmall,
            color = ResumeColors.SecondaryText
        )
    }
}

@Composable
private fun RetrySection() {
    var status by remember { mutableStateOf("Idle") }
    var attempts by remember { mutableStateOf(0) }
    var log by remember { mutableStateOf(listOf<String>()) }
    val scope = rememberCoroutineScope()

    SectionCard(title = "Retry (Exponential Backoff)", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Retry failed operations with exponentially increasing delays (500ms → 1s → 2s).", "Retenta operações falhas com delays exponencialmente crescentes (500ms → 1s → 2s).")
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.dp8)) {
            Button(
                onClick = {
                    status = "Running..."
                    attempts = 0
                    log = emptyList()
                    scope.launch {
                        try {
                            val result = RetryStrategy.withExponentialBackoff(
                                maxRetries = 3,
                                initialDelayMs = 500L,
                                onRetry = { attempt, e ->
                                    log = log + "Attempt $attempt failed: ${e.message}"
                                }
                            ) { attempt ->
                                attempts = attempt
                                if (attempt < 3) throw Exception("Simulated error")
                                "Success on attempt $attempt!"
                            }
                            status = result
                        } catch (e: Exception) {
                            status = "Failed: ${e.message}"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Primary),
                enabled = status != "Running..."
            ) { Text("Simulate Fail→Success") }

            Button(
                onClick = {
                    status = "Running..."
                    attempts = 0
                    log = emptyList()
                    scope.launch {
                        try {
                            RetryStrategy.withExponentialBackoff(
                                maxRetries = 3,
                                initialDelayMs = 300L,
                                onRetry = { attempt, e ->
                                    log = log + "Attempt $attempt failed"
                                }
                            ) { attempt ->
                                attempts = attempt
                                throw Exception("Always fails")
                            }
                        } catch (e: Exception) {
                            status = "All retries exhausted"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.PrimaryDark),
                enabled = status != "Running..."
            ) { Text("Simulate All Fail") }
        }

        Spacer(modifier = Modifier.height(Spacing.dp8))
        Text(
            text = status,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = when {
                status.startsWith("Success") -> ResumeColors.Accent
                status.startsWith("Failed") || status.startsWith("All") -> ResumeColors.Primary
                else -> ResumeColors.SecondaryText
            }
        )
        if (attempts > 0) {
            Text("Attempts: $attempts", style = MaterialTheme.typography.bodyMedium)
        }
        log.forEach { entry ->
            Text(entry, style = MaterialTheme.typography.bodySmall, color = ResumeColors.SecondaryText)
        }
    }
}

@Composable
private fun EventBusSection() {
    var lastEvent by remember { mutableStateOf("No events yet") }
    var eventCount by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        EventBus.subscribe<String>().collect { event ->
            lastEvent = event
            eventCount++
        }
    }

    SectionCard(title = "Event Bus", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Decoupled communication between components using SharedFlow. Emit and subscribe to typed events.", "Comunicação desacoplada entre componentes usando SharedFlow. Emite e escuta eventos tipados.")
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.dp6)) {
            Button(
                onClick = { EventBus.tryEmit("UserClicked at ${DateUtils.formatDate(DateUtils.now(), "HH:mm:ss")}") },
                colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Primary)
            ) { Text("Emit Event") }
            Button(
                onClick = { EventBus.tryEmit("DataLoaded with 42 items") },
                colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Accent)
            ) { Text("Emit Another") }
        }
        Spacer(modifier = Modifier.height(Spacing.dp8))
        Text("Events received: $eventCount", style = MaterialTheme.typography.bodySmall, color = ResumeColors.SecondaryText)
        Text(
            text = lastEvent,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = ResumeColors.Primary
        )
    }
}

@Composable
private fun HapticSection(lang: AppLanguage) {
    SectionCard(title = "Haptic Feedback", modifier = Modifier.padding(horizontal = Spacing.dp2)) {
        Desc("Trigger device vibration using platform-native APIs (Vibrator / UIImpactFeedbackGenerator).", "Dispara vibração do dispositivo usando APIs nativas (Vibrator / UIImpactFeedbackGenerator).")
        Button(
            onClick = { hapticFeedback() },
            colors = ButtonDefaults.buttonColors(containerColor = ResumeColors.Primary)
        ) {
            Text(if (lang == AppLanguage.PT) "Vibrar" else "Vibrate")
        }
    }
}
