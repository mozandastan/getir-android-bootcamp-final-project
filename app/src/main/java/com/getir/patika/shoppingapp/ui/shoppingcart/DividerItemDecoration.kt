import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.getir.patika.shoppingapp.R

class DividerItemDecoration(private val context: Context,private val dividerHeightDP: Int = 1,
                            private val dividerMarginDP: Int = 12) : RecyclerView.ItemDecoration() {

    private val dividerPaint = Paint()

    init {
        //Set line color
        dividerPaint.color = ContextCompat.getColor(context, R.color.stroke_grey)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // Add margin to the top and bottom of the divider
        outRect.set(0, dividerMarginDP.dpToPx(), 0, dividerMarginDP.dpToPx())
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // Convert height and margin from dp to pixels
        val dividerHeightPx = dividerHeightDP.dpToPx()
        val dividerMarginPx = dividerMarginDP.dpToPx()
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        //Draw loop
        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + dividerMarginPx
            val bottom = top + dividerHeightPx
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), dividerPaint)
        }
    }
    private fun Int.dpToPx(): Int {
        // Convert dp to pixels
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
            context.resources.displayMetrics).toInt()
    }
}
