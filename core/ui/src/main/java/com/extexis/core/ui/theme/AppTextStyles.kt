package com.extexis.core.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTextStyles {

    val Hero = TextStyle(
        fontFamily = DMSans,
        fontSize = 26.sp,
        lineHeight = 34.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.3).sp,
    )
    val Title = TextStyle(
        fontFamily = DMSans,
        fontSize = 22.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.2).sp,
    )
    val SectionHeading = TextStyle(
        fontFamily = DMSans,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
    )
    val CardTitle = TextStyle(
        fontFamily = DMSans,
        fontSize = 15.sp,
        lineHeight = 21.sp,
        fontWeight = FontWeight.Bold,
    )
    val ButtonLabel = TextStyle(
        fontFamily = DMSans,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Bold,
    )
    val BodyLarge = TextStyle(
        fontFamily = DMSans,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Normal,
    )
    val Body = TextStyle(
        fontFamily = DMSans,
        fontSize = 13.sp,
        lineHeight = 19.sp,
        fontWeight = FontWeight.Normal,
    )
    val BodyMedium = TextStyle(
        fontFamily = DMSans,
        fontSize = 13.sp,
        lineHeight = 19.sp,
        fontWeight = FontWeight.Medium,
    )
    val Meta = TextStyle(
        fontFamily = DMSans,
        fontSize = 11.sp,
        lineHeight = 15.sp,
        fontWeight = FontWeight.Medium,
    )
    val Caption = TextStyle(
        fontFamily = DMSans,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.2.sp,
    )
    val LabelCaps = TextStyle(
        fontFamily = DMSans,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp,
    )

    val HeadLine1 = Hero
    val HeadLine2 = Title
    val HeadLine3 = SectionHeading
    val HeadingDisplay = Hero
    val HeadingHero = Title
    val HeadingLarge = SectionHeading
    val HeadingMedium = CardTitle
    val HeadingSmall = BodyMedium
    val BodyText1Regular = BodyLarge
    val BodyText1Medium = BodyLarge.copy(fontWeight = FontWeight.Medium)
    val LabelMedium = Body.copy(fontWeight = FontWeight.Medium)
    val BodyText2Regular = Body
    val BodyText2Medium = BodyMedium
    val BodyText2Bold = Body.copy(fontWeight = FontWeight.Bold)
    val BodyText3Regular = Meta.copy(fontSize = 12.sp, lineHeight = 16.sp)
    val BodyText3Medium = Meta.copy(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium)
    val BodyText3Bold = Meta.copy(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Bold)
    val LabelSmall = Meta
    val LabelTiny = Caption
    val MoneyLarge = Hero.copy(fontSize = 36.sp, lineHeight = 42.sp)
    val MoneyMedium = Title.copy(fontSize = 24.sp, lineHeight = 30.sp)
    val MoneySmall = CardTitle.copy(fontSize = 20.sp, lineHeight = 26.sp)
    val MoneyMini = CardTitle
    val ChallengeTitle = SectionHeading
}
