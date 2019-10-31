package com.thirdmono.reddit.data.entity

import com.google.gson.annotations.SerializedName

data class SubReddit(
        @SerializedName("banner_img")
        val bannerImg: String? = null,
        @SerializedName("user_sr_theme_enabled")
        val userSrThemeEnabled: Boolean? = null,
        @SerializedName("submit_text_html")
        val submitTextHtml: String? = null,
        @SerializedName("user_is_banned")
        val userIsBanned: Any? = null,
        @SerializedName("wiki_enabled")
        val wikiEnabled: Boolean? = null,
        @SerializedName("show_media")
        val showMedia: Boolean? = null,
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("submit_text")
        val submitText: String? = null,
        @SerializedName("display_name")
        val displayName: String? = null,
        @SerializedName("header_img")
        val headerImg: String? = null,
        @SerializedName("description_html")
        val descriptionHtml: String? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("collapse_deleted_comments")
        val collapseDeletedComments: Boolean? = null,
        @SerializedName("over18")
        val over18: Boolean? = null,
        @SerializedName("public_description_html")
        val publicDescriptionHtml: String? = null,
        @SerializedName("spoilers_enabled")
        val spoilersEnabled: Boolean? = null,
        @SerializedName("icon_size")
        val iconSize: List<Int>? = null,
        @SerializedName("suggested_comment_sort")
        val suggestedCommentSort: Any? = null,
        @SerializedName("icon_img")
        val iconImg: String? = null,
        @SerializedName("header_title")
        val headerTitle: String? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("user_is_muted")
        val userIsMuted: Any? = null,
        @SerializedName("submit_link_label")
        val submitLinkLabel: Any? = null,
        @SerializedName("accounts_active")
        val accountsActive: Any? = null,
        @SerializedName("public_traffic")
        val publicTraffic: Boolean? = null,
        @SerializedName("header_size")
        val headerSize: List<Int>? = null,
        @SerializedName("subscribers")
        val subscribers: Int? = null,
        @SerializedName("submit_text_label")
        val submitTextLabel: Any? = null,
        @SerializedName("lang")
        val lang: String? = null,
        @SerializedName("user_is_moderator")
        val userIsModerator: Any? = null,
        @SerializedName("key_color")
        val keyColor: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("created")
        val created: Double? = null,
        @SerializedName("url")
        val url: String? = null,
        @SerializedName("quarantine")
        val quarantine: Boolean? = null,
        @SerializedName("hide_ads")
        val hideAds: Boolean? = null,
        @SerializedName("created_utc")
        val createdUtc: Double? = null,
        @SerializedName("banner_size")
        val bannerSize: Any? = null,
        @SerializedName("user_is_contributor")
        val userIsContributor: Any? = null,
        @SerializedName("public_description")
        val publicDescription: String? = null,
        @SerializedName("show_media_preview")
        val showMediaPreview: Boolean? = null,
        @SerializedName("comment_score_hide_mins")
        val commentScoreHideMins: Int? = null,
        @SerializedName("subreddit_type")
        val subredditType: String? = null,
        @SerializedName("submission_type")
        val submissionType: String? = null,
        @SerializedName("user_is_subscriber")
        val userIsSubscriber: Any? = null
)