package com.spielberg.commonext

import android.animation.Animator
import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView


fun View.goneExt() {
    visibility = View.GONE
}

fun View.visibleExt() {
    visibility = View.VISIBLE
}

fun View.invisibleExt() {
    visibility = View.INVISIBLE
}

val View.isGoneExt: Boolean
    get() {
        return visibility == View.GONE
    }

val View.isVisibleExt: Boolean
    get() {
        return visibility == View.VISIBLE
    }

val View.isInvisibleExt: Boolean
    get() {
        return visibility == View.INVISIBLE
    }

fun View.toggleVisibilityExt() {
    visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
}

fun View.getViewLocationArrExt(): IntArray {
    val viewLoc = intArrayOf(0, 0)
    getLocationOnScreen(viewLoc)
    return viewLoc
}

fun View.hasLeftSpaceExt(popupContentView: View): Boolean {
    val viewLoc = getViewLocationArrExt()
    if (viewLoc[0] <= popupContentView.measuredWidth) {
        return false
    }
    return true
}

fun View.hasRightSpaceExt(mCtx: Context, popupContentView: View): Boolean {
    val viewLoc = getViewLocationArrExt()
    if (mCtx.getScreenWidthExt() - viewLoc[0] - width <= popupContentView.measuredWidth) {
        return false
    }
    return true
}

fun View.hasTopSpaceExt(popupContentView: View): Boolean {
    val viewLoc = getViewLocationArrExt()
    if (viewLoc[1] <= popupContentView.measuredHeight) {
        return false
    }
    return true
}

fun View.hasBottomSpaceExt(mCtx: Context, popupContentView: View): Boolean {
    val viewLoc = getViewLocationArrExt()
    if (mCtx.getScreenHeightExt() - viewLoc[1] - height <= popupContentView.measuredHeight) {
        return false
    }
    return true
}

fun View.toBitmapExt(): Bitmap {
    if (measuredWidth == 0 || measuredHeight == 0) {
        throw RuntimeException("调用该方法时，请确保View已经测量完毕，如果宽高为0，则抛出异常以提醒！")
    }
    return when (this) {
        is RecyclerView -> {
            this.scrollToPosition(0)
            this.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )

            val bmp = Bitmap.createBitmap(width, measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)

            //draw default bg, otherwise will be black
            if (background != null) {
                background.setBounds(0, 0, width, measuredHeight)
                background.draw(canvas)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            this.draw(canvas)
            //恢复高度
            this.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST)
            )
            bmp //return
        }
        else -> {
            val screenshot =
                Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_4444)
            val canvas = Canvas(screenshot)
            if (background != null) {
                background.setBounds(0, 0, width, measuredHeight)
                background.draw(canvas)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            draw(canvas)
            screenshot
        }
    }
}

fun View.addOnGlobalLayoutListenerExt(onGlobalLayout: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            onGlobalLayout()
        }
    })
}

fun View.widthExt(width: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.width = width
    layoutParams = params
    return this
}

fun View.heightExt(height: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.height = height
    layoutParams = params
    return this
}

fun View.limitWidthExt(w: Int, min: Int, max: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    when {
        w < min -> params.width = min
        w > max -> params.width = max
        else -> params.width = w
    }
    layoutParams = params
    return this
}

fun View.limitHeightExt(h: Int, min: Int, max: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    when {
        h < min -> params.height = min
        h > max -> params.height = max
        else -> params.height = h
    }
    layoutParams = params
    return this
}

fun View.widthAndHeightExt(width: Int, height: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.width = width
    params.height = height
    layoutParams = params
    return this
}

/**
 * 设置宽度，带有过渡动画
 * @param targetValue 目标宽度
 * @param duration 时长
 * @param action 可选行为
 */
fun View.animateWidthExt(
    targetValue: Int, duration: Long = 400, listener: Animator.AnimatorListener? = null,
    action: ((Float) -> Unit)? = null
) {
    post {
        ValueAnimator.ofInt(width, targetValue).apply {
            addUpdateListener {
                widthExt(it.animatedValue as Int)
                action?.invoke((it.animatedFraction))
            }
            if (listener != null) addListener(listener)
            setDuration(duration)
            start()
        }
    }
}

/**
 * 设置高度，带有过渡动画
 * @param targetValue 目标高度
 * @param duration 时长
 * @param action 可选行为
 */
fun View.animateHeightExt(
    targetValue: Int,
    duration: Long = 400,
    listener: Animator.AnimatorListener? = null,
    action: ((Float) -> Unit)? = null
) {
    post {
        ValueAnimator.ofInt(height, targetValue).apply {
            addUpdateListener {
                heightExt(it.animatedValue as Int)
                action?.invoke((it.animatedFraction))
            }
            if (listener != null) addListener(listener)
            setDuration(duration)
            start()
        }
    }
}

/**
 * 设置宽度和高度，带有过渡动画
 * @param targetWidth 目标宽度
 * @param targetHeight 目标高度
 * @param duration 时长
 * @param action 可选行为
 */
fun View.animateWidthAndHeightExt(
    targetWidth: Int,
    targetHeight: Int,
    duration: Long = 400,
    listener: Animator.AnimatorListener? = null,
    action: ((Float) -> Unit)? = null
) {
    post {
        val startHeight = height
        val evaluator = IntEvaluator()
        ValueAnimator.ofInt(width, targetWidth).apply {
            addUpdateListener {
                widthAndHeightExt(
                    it.animatedValue as Int,
                    evaluator.evaluate(it.animatedFraction, startHeight, targetHeight)
                )
                action?.invoke((it.animatedFraction))
            }
            if (listener != null) addListener(listener)
            setDuration(duration)
            start()
        }
    }
}

fun View.makeMeasureSpecExt(measureSpec: Int): Int = when (measureSpec) {
    ViewGroup.LayoutParams.WRAP_CONTENT ->
        View.MeasureSpec.makeMeasureSpec(
            View.MeasureSpec.getSize(measureSpec),
            View.MeasureSpec.AT_MOST
        )
    else ->
        View.MeasureSpec.makeMeasureSpec(
            View.MeasureSpec.getSize(measureSpec),
            View.MeasureSpec.EXACTLY
        )
}

/**
 * 防止重复点击事件 默认0.5秒内不可重复点击
 * @param interval 时间间隔 默认0.5秒
 * @param action 执行方法
 */
var lastClickTime = 0L
fun View.clickNoRepeat(interval: Long = 500, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        action(it)
    }
}

fun View.getViewLocationArr(): IntArray {
    val viewLoc = intArrayOf(0, 0)
    getLocationOnScreen(viewLoc)
    return viewLoc
}

fun View.hasLeftSpace(popupContentView: View): Boolean {
    val viewLoc = getViewLocationArr()
    if (viewLoc[0] <= popupContentView.measuredWidth) {
        return false
    }
    return true
}

fun View.hasRightSpace(mCtx: Context, popupContentView: View): Boolean {
    val viewLoc = getViewLocationArr()
    if (mCtx.getScreenWidthExt() - viewLoc[0] - width <= popupContentView.measuredWidth) {
        return false
    }
    return true
}

fun View.hasTopSpace(popupContentView: View): Boolean {
    val viewLoc = getViewLocationArr()
    if (viewLoc[1] <= popupContentView.measuredHeight) {
        return false
    }
    return true
}

fun View.hasBottomSpace(mCtx: Context, popupContentView: View): Boolean {
    val viewLoc = getViewLocationArr()
    if (mCtx.getScreenHeightExt() - viewLoc[1] - height <= popupContentView.measuredHeight) {
        return false
    }
    return true
}

/**
 * 获取 View context 所属的 Activity
 * @return [Activity]
 */
fun View?.getViewActivity(): Activity? {
    if (this == null) return null
    try {
        var context = this.context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * 获取指定 View 父布局
 * @param view [View]
 * @param <T>  泛型
 * @return [View]
</T> */
fun <T : View?> getParent(view: View?): T? {
    if (view != null) {
        try {
            return view.parent as T
        } catch (e: java.lang.Exception) {
           e.printStackTrace()
        }
    }
    return null
}

/**
 * 获取是否限制子 View 在其边界内绘制
 * @return `true` yes, `false` no
 */
fun ViewGroup?.getClipChildren(): Boolean {
    if (this != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return this.clipChildren
        }
    }
    return true
}

/**
 * 获取指定索引 View
 * @param viewGroup [ViewGroup]
 * @param <T>       泛型
 * @return [View]
</T> */
fun <T : View?> getChildAt(viewGroup: ViewGroup?): T? {
    return getChildAt(viewGroup, 0)
}

/**
 * 获取最后一个索引 View
 * @param viewGroup [ViewGroup]
 * @param <T>       泛型
 * @return [View]
</T> */
fun <T : View?> getChildAtLast(viewGroup: ViewGroup?): T? {
    return if (viewGroup == null) null else getChildAt(viewGroup, viewGroup.childCount - 1)
}

/**
 * 获取指定索引 View
 * @param viewGroup [ViewGroup]
 * @param index     索引
 * @param <T>       泛型
 * @return [View]
</T> */
fun <T : View?> getChildAt(
    viewGroup: ViewGroup?,
    index: Int
): T? {
    if (viewGroup != null && index >= 0) {
        try {
            return viewGroup.getChildAt(index) as T
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    return null
}

/**
 * View 内容滚动位置 ( 相对于初始位置移动 )
 * <pre>
 * 无滚动过程
</pre> *
 * @param view [View]
 * @param x    X 轴开始坐标
 * @param y    Y 轴开始坐标
 * @return [View]
 */
fun scrollTo(
    view: View?,
    x: Int,
    y: Int
): View? {
    view?.scrollTo(x, y)
    return view
}

/**
 * View 内部滚动位置 ( 相对于上次移动的最后位置移动 )
 * <pre>
 * 无滚动过程
</pre> *
 * @param view [View]
 * @param x    X 轴开始坐标
 * @param y    Y 轴开始坐标
 * @return [View]
 */
fun scrollBy(
    view: View?,
    x: Int,
    y: Int
): View? {
    view?.scrollBy(x, y)
    return view
}

/**
 * 设置 ViewGroup 和其子控件两者之间的关系
 * <pre>
 * beforeDescendants : ViewGroup 会优先其子类控件而获取到焦点
 * afterDescendants : ViewGroup 只有当其子类控件不需要获取焦点时才获取焦点
 * blocksDescendants : ViewGroup 会覆盖子类控件而直接获得焦点
 * android:descendantFocusability="blocksDescendants"
</pre> *
 * @param viewGroup    [ViewGroup]
 * @param focusability [ViewGroup.FOCUS_BEFORE_DESCENDANTS]、[ViewGroup.FOCUS_AFTER_DESCENDANTS]、[ViewGroup.FOCUS_BLOCK_DESCENDANTS]
 * @param <T>          泛型
 * @return [ViewGroup]
</T> */
fun <T : ViewGroup?> setDescendantFocusability(
    viewGroup: T?,
    focusability: Int
): T? {
    try {
        if (viewGroup != null) viewGroup.descendantFocusability = focusability
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return viewGroup
}

/**
 * 设置 View 是否启用
 * @param enabled `true` 启用, `false` 禁用
 * @param views   View[]
 * @return `true` 启用, `false` 禁用
 */
fun setEnabled(
    enabled: Boolean,
    vararg views: View?
): Boolean {
    var i = 0
    val len = views.size
    while (i < len) {
        val view = views[i]
        if (view != null) {
            view.isEnabled = enabled
        }
        i++
    }
    return enabled
}

/**
 * 设置 View 是否可以点击
 * @param clickable `true` 可点击, `false` 不可点击
 * @param views     View[]
 * @return `true` 可点击, `false` 不可点击
 */
fun setClickable(
    clickable: Boolean,
    vararg views: View?
): Boolean {
    var i = 0
    val len = views.size
    while (i < len) {
        val view = views[i]
        if (view != null) {
            view.isClickable = clickable
        }
        i++
    }
    return clickable
}

/**
 * 判断 View 是否显示 ( 如果存在父级则判断父级 )
 * <pre>
 * 需要父布局已展示到 Window 上
</pre> *
 * @param view [View]
 * @return `true` yes, `false` no
 */
fun isShown(view: View?): Boolean {
    return view != null && view.isShown
}

/**
 * 判断 View 是否都显示 ( 如果存在父级则判断父级 )
 * @param views View[]
 * @return `true` yes, `false` no
 */
fun isShowns(vararg views: View?): Boolean {
    if (views.isNotEmpty()) {
        var i = 0
        val len = views.size
        while (i < len) {
            if (!isShown(views[i])) {
                return false
            }
            i++
        }
        return true
    }
    return false
}

/**
 * 获取 View Margin
 * @return new int[] {left, top, right, bottom}
 */
fun View?.getMargin(): IntArray {
    val margin = intArrayOf(0, 0, 0, 0)
    if (this != null && this.layoutParams != null) {
        if (this.layoutParams is MarginLayoutParams) {
            val layoutParams = this.layoutParams as MarginLayoutParams
            margin[0] = layoutParams.leftMargin
            margin[1] = layoutParams.topMargin
            margin[2] = layoutParams.rightMargin
            margin[3] = layoutParams.bottomMargin
        }
    }
    return margin
}

/**
 * 获取 View Padding
 * @param view [View]
 * @return new int[] {left, top, right, bottom}
 */
fun View?.getPadding(): IntArray {
    val padding = intArrayOf(0, 0, 0, 0)
    if (this != null) {
        padding[0] = this.paddingLeft
        padding[1] = this.paddingTop
        padding[2] = this.paddingRight
        padding[3] = this.paddingBottom
    }
    return padding
}