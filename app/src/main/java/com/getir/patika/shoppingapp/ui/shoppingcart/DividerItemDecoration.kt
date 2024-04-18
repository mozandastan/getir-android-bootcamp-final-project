import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.getir.patika.shoppingapp.R

class DividerItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    private val dividerHeight = 1
    private val dividerMargin = 12
    private val dividerPaint = Paint()

    init {
        dividerPaint.color = ContextCompat.getColor(context, R.color.stroke_grey)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, dividerMargin.dpToPx(), 0, dividerMargin.dpToPx())
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + dividerMargin.dpToPx()
            val bottom = top + dividerHeight.dpToPx()
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), dividerPaint)
        }
    }
    private fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
    }
}
