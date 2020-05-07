package com.zen.alchan.ui.animelist.list

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zen.alchan.R
import com.zen.alchan.data.response.MediaList
import com.zen.alchan.helper.libs.GlideApp
import com.zen.alchan.helper.pojo.ListStyle
import com.zen.alchan.helper.removeTrailingZero
import com.zen.alchan.helper.secondsToDateTime
import com.zen.alchan.helper.setRegularPlural
import com.zen.alchan.helper.utils.AndroidUtility
import com.zen.alchan.helper.utils.DialogUtility
import kotlinx.android.synthetic.main.list_anime_list_grid.view.*
import type.ScoreFormat

class AnimeListGridRvAdapter(private val context: Context,
                             private val list: List<MediaList>,
                             private val scoreFormat: ScoreFormat,
                             private val listStyle: ListStyle?,
                             private val listener: AnimeListListener
) : RecyclerView.Adapter<AnimeListGridRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_anime_list_grid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mediaList = list[position]

        GlideApp.with(context).load(mediaList.media?.coverImage?.extraLarge).into(holder.animeCoverImage)
        holder.animeTitleText.text = mediaList.media?.title?.userPreferred
        holder.animeFormatText.text = mediaList.media?.format?.name?.replace('_', ' ')

        if (mediaList.media?.nextAiringEpisode != null) {
            holder.animeAiringLayout.visibility = View.VISIBLE
            if (mediaList.media?.nextAiringEpisode?.episode!! > mediaList.progress!! + 1) {
                GlideApp.with(context).load(R.drawable.ic_spam).into(holder.animeAiringIcon)
            } else {
                GlideApp.with(context).load(R.drawable.ic_filled_circle).into(holder.animeAiringIcon)
            }
        } else {
            holder.animeAiringLayout.visibility = View.GONE
        }

        if (scoreFormat == ScoreFormat.POINT_3) {
            GlideApp.with(context).load(AndroidUtility.getSmileyFromScore(mediaList.score)).into(holder.animeStarIcon)
            holder.animeStarIcon.imageTintList = ColorStateList.valueOf(AndroidUtility.getResValueFromRefAttr(context, R.attr.themePrimaryColor))
            holder.animeRatingText.text = ""
        } else {
            GlideApp.with(context).load(R.drawable.ic_star_filled).into(holder.animeStarIcon)
            holder.animeStarIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.yellowStar))
            holder.animeRatingText.text = mediaList.score?.removeTrailingZero()
        }

        holder.animeProgressText.text = "${mediaList.progress}/${mediaList.media?.episodes ?: '?'}"

        holder.animeAiringLayout.setOnClickListener {
            var message = "Ep ${mediaList.media?.nextAiringEpisode?.episode} on ${mediaList.media?.nextAiringEpisode?.airingAt?.secondsToDateTime()}."
            val epDiff = mediaList.media?.nextAiringEpisode?.episode!! - mediaList.progress!!
            if (epDiff > 1) {
                message += "\nYou are ${epDiff - 1} ${"episode".setRegularPlural(epDiff - 1)} behind."
            }
            DialogUtility.showToast(context, message, Toast.LENGTH_LONG
            )
        }

        holder.animeProgressLayout.setOnClickListener {
            listener.openProgressDialog(mediaList)
        }

        holder.animeScoreLayout.setOnClickListener {
            listener.openScoreDialog(mediaList)
        }

        holder.animeCoverImage.setOnClickListener {
            listener.openEditor(mediaList.id)
        }

        holder.animeTitleLayout.setOnClickListener {
            listener.openBrowsePage(mediaList.media!!)
        }

        if (listStyle?.cardColor != null) {
            holder.listCardBackground.setCardBackgroundColor(Color.parseColor(listStyle.cardColor))

            // 6 is color hex code length without the alpha
            val transparentCardColor =  "#CC" + listStyle.cardColor?.substring(listStyle.cardColor?.length!! - 6)
            holder.animeTitleLayout.setCardBackgroundColor(Color.parseColor(transparentCardColor))
            holder.animeFormatLayout.setCardBackgroundColor(Color.parseColor(transparentCardColor))
            holder.animeAiringLayout.setCardBackgroundColor(Color.parseColor(transparentCardColor))
            holder.animeScoreLayout.setCardBackgroundColor(Color.parseColor(transparentCardColor))
            holder.animeProgressLayout.setCardBackgroundColor(Color.parseColor(transparentCardColor))
        }

        if (listStyle?.primaryColor != null) {
            holder.animeTitleText.setTextColor(Color.parseColor(listStyle.primaryColor))
            holder.animeRatingText.setTextColor(Color.parseColor(listStyle.primaryColor))
            holder.animeProgressText.setTextColor(Color.parseColor(listStyle.primaryColor))

            if (scoreFormat == ScoreFormat.POINT_3) {
                holder.animeStarIcon.imageTintList = ColorStateList.valueOf(Color.parseColor(listStyle.primaryColor))
            }
        }

        if (listStyle?.secondaryColor != null) {
            holder.animeAiringIcon.imageTintList = ColorStateList.valueOf(Color.parseColor(listStyle.secondaryColor))
        }

        if (listStyle?.textColor != null) {
            holder.animeFormatText.setTextColor(Color.parseColor(listStyle.textColor))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listCardBackground = view.listCardBackground!!
        val animeTitleText = view.animeTitleText!!
        val animeTitleLayout = view.animeTitleLayout!!
        val animeCoverImage = view.animeCoverImage!!
        val animeFormatText = view.animeFormatText!!
        val animeFormatLayout = view.animeFormatLayout!!
        val animeProgressText = view.animeProgressText!!
        val animeScoreLayout = view.animeScoreLayout!!
        val animeStarIcon = view.animeStarIcon!!
        val animeRatingText = view.animeRatingText!!
        val animeProgressLayout = view.animeProgressLayout!!
        val animeAiringLayout = view.animeAiringLayout!!
        val animeAiringIcon = view.animeAiringIcon!!
    }
}