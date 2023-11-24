package com.se.dr.bo.seconddrawingboard.wight

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlendMode
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Paint.Cap
import android.graphics.PathEffect
import android.graphics.PixelFormat
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Shader
import android.graphics.Xfermode
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.annotation.RequiresApi
import com.se.dr.bo.seconddrawingboard.R
import java.util.LinkedList

/**
 * @author [Jenly](mailto:jenly1314@gmail.com)
 */
class DrawBoardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    /**
     * 触摸时允许的容差值
     *
     * @return
     */
    /**
     * 设置触摸时允许的容差值
     *
     * @param touchTolerance
     */
    /**
     * 触摸时允许的容差值
     */
    var touchTolerance = 0f
    /**
     * 触摸点的比例
     *
     * @return
     */
    /**
     * 设置触摸点的比例
     *
     * @param touchPointRatio
     */
    /**
     * 触摸点的比例
     */
    var touchPointRatio = 0f

    /**
     * 原始图片
     */
    private var originBitmap: Bitmap? = null

    /**
     * 绘制层图片
     */
    private var drawingBitmap: Bitmap? = null

    /**
     * 预览层图片
     */
    private var previewBitmap: Bitmap? = null

    /**
     * 触摸层画布
     */
    private var touchBitmap: Bitmap? = null

    /**
     * 矩阵：主要用于对图片进行移动和缩放相关的处理
     */
    private var matrix: Matrix? = null

    /**
     * 绘制层画布
     */
    private var drawingCanvas: Canvas? = null

    /**
     * 预览层画布
     */
    private var previewCanvas: Canvas? = null

    /**
     * 触摸层画布
     */
    private var touchCanvas: Canvas? = null

    /**
     * 是否改变图片
     */
    private var isChangeBitmap = false

    /**
     * 是否触摸
     */
    private var isTouch = false

    /**
     * 画笔
     */
    private var paint: Paint? = null

    /**
     * 触摸点画笔
     */
    private var pointPaint: Paint? = null
    /**
     * 获取画笔颜色
     *
     * @return
     */
    /**
     * 设置画笔颜色
     *
     * @param paintColor
     */
    /**
     * 画笔的颜色
     */
    var paintColor = Color.BLACK
    /**
     * 获取触摸点的颜色
     *
     * @return
     */
    /**
     * 设置触摸点的颜色
     *
     * @param touchPointColor
     */
    /**
     * 触摸点画笔的颜色
     */
    var touchPointColor = -0x50333334

    /**
     * 绘制文本的字体大小
     */
    private var drawTextSize = 0f

    /**
     * 文本是否是粗体
     */
    private var isFakeBoldText = false

    /**
     * 文本是否需要下划线
     */
    private var isUnderlineText = false

    /**
     * 画笔线条描边宽度
     */
    private var lineStrokeWidth = 0f

    /**
     * 橡皮擦描边宽度
     */
    private var eraserStrokeWidth = 0f

    /**
     * 缩放点描边宽度
     */
    private var zoomPointStrokeWidth = 0f
    /**
     * 设置画笔的 Paint.Style
     *
     * @param paintStyle
     */
    /**
     * 画笔的 Paint.Style
     */
    var paintStyle = Paint.Style.STROKE
    /**
     * 设置画笔的 Shader
     *
     * @param paintShader
     */
    /**
     * 画笔的着色器
     */
    var paintShader: Shader? = null

    /**
     * 是否圆角
     */
    private var paintStrokeCap = Cap.SQUARE

    /**
     * 是否有模糊效果
     */
    private var isBlurMask = false
    /**
     * 获取画笔的 Xfermode
     *
     * @return
     */
    /**
     * 设置画笔的 Xfermode
     *
     * @param xfermode
     */
    /**
     * 画笔的 Xfermode
     */
    var paintXfermode: Xfermode? = null
    /**
     * 设置画笔的 PathEffect
     *
     * @param pathEffect
     */
    /**
     * 画笔的 PathEffect
     */
    var pathEffect: PathEffect? = null
    /**
     * 设置画笔的 BlendMode
     *
     * @param blendMode
     */
    /**
     * 画笔的 BlendMode
     */
    @set:RequiresApi(Build.VERSION_CODES.Q)
    var blendMode: BlendMode? = null
    /**
     * 获取绘图模式
     *
     * @return
     */
    /**
     * 设置绘图模式
     *
     * @param drawMode
     */
    /**
     * 绘图模式
     */
    @get:DrawMode
    @DrawMode
    var drawMode = DrawMode.DRAW_PATH

    /**
     * 是否绘制
     */
    private var isDraw = false

    /**
     * 是否是缩放
     */
    private var isZoom = false

    /**
     * 控件的宽度
     */
    private var width = 0

    /**
     * 控件的高度
     */
    private var height = 0

    /**
     * 最后一次的横坐标值
     */
    private var lastX = 0f

    /**
     * 最后一次的纵坐标值
     */
    private var lastY = 0f

    /**
     * 最后一次中心点的横坐标值
     */
    private var lastCenterPointX = 0f

    /**
     * 最后一次中心点的纵坐标值
     */
    private var lastCenterPointY = 0f

    /**
     * 最后一次两指之间的距离
     */
    private var lastFingerDistance = 0f

    /**
     * 支持最小的放大倍数
     */
    private var minZoom = 1f

    /**
     * 支持最大的放大倍数
     */
    private val maxZoom = 4f
    /**
     * 是否自适应
     *
     * @return
     */
    /**
     * 设置是否自适应
     *
     * @param fit
     */
    /**
     * 是否自适应
     */
    var isFit = true
    /**
     * 获取当前图片的宽度，缩放的宽度
     *
     * @return
     */
    /**
     * 当前图片的宽度，缩放时此值会发生变化
     */
    var currentBitmapWidth = 0
        private set
    /**
     * 获取当前图片的高度，缩放的高度
     *
     * @return
     */
    /**
     * 当前图片的高度，缩放时此值会发生变化
     */
    var currentBitmapHeight = 0
        private set

    /**
     * 手指在横坐标方向上的移动距离
     */
    private var movedDistanceX = 0f

    /**
     * 手指在纵坐标方向上的移动距离
     */
    private var movedDistanceY = 0f
    /**
     * 获取当前图片在矩阵上的横向偏移值
     *
     * @return
     */
    /**
     * 当前图片在矩阵上的横向偏移值
     */
    var currentTranslateX = 0f
        private set
    /**
     * 获取当前图片在矩阵上的纵向偏移值
     *
     * @return
     */
    /**
     * 当前图片在矩阵上的纵向偏移值
     */
    var currentTranslateY = 0f
        private set
    /**
     * 真实的变焦倍数
     *
     * @return
     */
    /**
     * 当前图片在矩阵上的缩放比例
     */
    var realZoom = 0f
        private set

    /**
     * 手指移动的距离所造成的缩放比例
     */
    private var scaledRatio = 0f

    /**
     * 图片初始化时的缩放比例
     */
    private var initRatio = 0f

    /**
     * 绘图记录：主要用于撤销和保存
     */
    private var drawList: LinkedList<Draw>? = null

    /**
     * 绘图记录备份：主要用于恢复
     */
    private var backupDrawList: MutableList<Draw>? = null

    /**
     * 是否撤销过
     */
    private var isRevoked = false

    /**
     * 绘图对象
     */
    private var draw: Draw? = null

    /**
     * 需要绘制的文本内容
     */
    private val drawText: String? = null

    /**
     * 需要绘制的位图
     */
    private val drawBitmap: Bitmap? = null

    /**
     * 锚点是否居中
     */
    private var isDrawBitmapAnchorCenter = false

    /**
     * 用于存储所有支持的绘图模式
     */
    private var drawMap: MutableMap<Int, Class<out Draw>>? = null
    /**
     * 是否启用绘图
     *
     * @return
     */
    /**
     * 设置是否启用绘图
     *
     * @param drawEnabled
     */
    /**
     * 是否启用绘图
     */
    var isDrawEnabled = true
    /**
     * 是否启用缩放
     *
     * @return
     */
    /**
     * 设置是否启用缩放
     *
     * @param zoomEnabled
     */
    /**
     * 是否启用缩放
     */
    var isZoomEnabled = true

    /**
     * 是否显示触摸点
     */
    private var isShowTouchPoint = true
    /**
     * 是否可撤销
     *
     * @return
     */
    /**
     * 是否可撤销
     */
    var isHasUndo = false
        private set
    /**
     * 是否可恢复
     *
     * @return
     */
    /**
     * 是否可恢复
     */
    var isHasRedo = false
        private set

    /**
     * 缩放监听
     */
    private var onZoomListener: OnZoomListener? = null

    /**
     * 绘制监听
     */
    private var onDrawListener: OnDrawListener? = null

    /**
     * 绘图模式
     */
    @IntDef(*[DrawMode.DRAW_PATH, DrawMode.ERASER, DrawMode.DRAW_CIRCLE])
    @Retention(AnnotationRetention.SOURCE)
    annotation class DrawMode {
        companion object {
            /**
             * 绘制路径
             */
            const val DRAW_PATH = 1
            const val DRAW_CIRCLE = 2

            /**
             * 橡皮擦
             */
            const val ERASER = 9
        }
    }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val displayMetrics = resources.displayMetrics
        drawTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15f, displayMetrics)
        lineStrokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, displayMetrics)
        eraserStrokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, displayMetrics)
        zoomPointStrokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6f, displayMetrics)
        touchTolerance = TOUCH_TOLERANCE
        touchPointRatio = TOUCH_POINT_RATIO
        val a = context.obtainStyledAttributes(attrs, R.styleable.DrawBoardView)
        val size = a.indexCount
        for (i in 0 until size) {
            val attr = a.getIndex(i)
            if (attr == R.styleable.DrawBoardView_dbvMinZoom) {
                minZoom = a.getFloat(attr, 1f)
            } else if (attr == R.styleable.DrawBoardView_dbvMaxZoom) {
                minZoom = a.getFloat(attr, 4f)
            } else if (attr == R.styleable.DrawBoardView_dbvFit) {
                isFit = a.getBoolean(attr, true)
            } else if (attr == R.styleable.DrawBoardView_dbvDrawEnabled) {
                isDrawEnabled = a.getBoolean(attr, true)
            } else if (attr == R.styleable.DrawBoardView_dbvZoomEnabled) {
                isZoomEnabled = a.getBoolean(attr, true)
            } else if (attr == R.styleable.DrawBoardView_dbvShowTouchPoint) {
                isShowTouchPoint = a.getBoolean(attr, true)
            } else if (attr == R.styleable.DrawBoardView_android_src) {
                val drawable = a.getDrawable(attr)
                if (drawable != null) {
                    originBitmap = getBitmapFormDrawable(drawable)
                    isChangeBitmap = true
                }
            } else if (attr == R.styleable.DrawBoardView_dbvPaintColor) {
                paintColor = a.getColor(attr, Color.RED)
            } else if (attr == R.styleable.DrawBoardView_dbvTouchPointColor) {
                touchPointColor = a.getColor(attr, Color.RED)
            } else if (attr == R.styleable.DrawBoardView_dbvDrawTextSize) {
                drawTextSize = a.getDimension(attr, drawTextSize)
            } else if (attr == R.styleable.DrawBoardView_dbvDrawTextBold) {
                isFakeBoldText = a.getBoolean(attr, false)
            } else if (attr == R.styleable.DrawBoardView_dbvDrawTextUnderline) {
                isUnderlineText = a.getBoolean(attr, false)
            } else if (attr == R.styleable.DrawBoardView_dbvTouchTolerance) {
                touchTolerance = a.getFloat(attr, TOUCH_TOLERANCE)
            }
        }
        a.recycle()
        pointPaint = Paint()
        pointPaint!!.style = Paint.Style.FILL_AND_STROKE
        pointPaint!!.isAntiAlias = true
        pointPaint!!.color = touchPointColor
        drawList = LinkedList()
        backupDrawList = ArrayList()
        matrix = Matrix()
        initDrawMap()
    }

    /**
     * 获取 [Bitmap]
     *
     * @param drawable
     * @return
     */
    private fun getBitmapFormDrawable(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, bitmap.width, bitmap.height)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 初始化支持的绘图模式
     */
    private fun initDrawMap() {
        drawMap = HashMap()
        (drawMap as HashMap<Int, Class<out Draw>>)[DrawMode.DRAW_PATH] = DrawPath::class.java
        (drawMap as HashMap<Int, Class<out Draw>>)[DrawMode.DRAW_CIRCLE] = DrawCircle::class.java
        (drawMap as HashMap<Int, Class<out Draw>>)[DrawMode.ERASER] = DrawPath::class.java
    }

    /**
     * 获取支持的绘图模式
     *
     * @return
     */
    fun getDrawMap(): Map<Int, Class<out Draw>>? {
        return drawMap
    }

    /**
     * 设置图片
     *
     * @param drawableId
     */
    fun setImageResource(@DrawableRes drawableId: Int) {
        imageBitmap = BitmapFactory.decodeResource(resources, drawableId)
    }

    var imageBitmap: Bitmap?
        /**
         * 获取图片（画板背景图层和画板图层合并后的图片）
         *
         * @return
         */
        get() {
            var bitmap: Bitmap? = null
            if (originBitmap != null) {
                bitmap = Bitmap.createBitmap(
                    originBitmap!!.width,
                    originBitmap!!.height,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                canvas.drawColor(Color.WHITE) // Fill the canvas with white color
                drawBitmap(canvas, Matrix(), false)
            }
            return bitmap
        }
        /**
         * 设置图片（画板背景图层）
         *
         * @param bitmap
         */
        set(bitmap) {
            if (bitmap != null) {
                originBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                drawList!!.clear()
                backupDrawList!!.clear()
                matrix!!.reset()
                isChangeBitmap = true
            } else {
                originBitmap = null
            }
            invalidate()
        }

    /**
     * 更新图片
     */
    @Synchronized
    private fun updateImageBitmap() {
        if (originBitmap == null) { //如果原始图片为空，则创建一个和控件视图宽高一致的图片
            originBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            //创建绘图层和预览层画布相关
            drawingBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            drawingCanvas = Canvas(drawingBitmap!!)
            previewBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            previewCanvas = Canvas(previewBitmap!!)
            touchBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            touchCanvas = Canvas(touchBitmap!!)
            matrix!!.reset()
            currentTranslateX = 0f
            currentTranslateY = 0f
            initRatio = 1f
            realZoom = 1f
            currentBitmapWidth = width
            currentBitmapHeight = height
            if (onZoomListener != null) {
                onZoomListener!!.onZoomUpdate(realZoom, getZoom())
            }
        } else if (isChangeBitmap) { //如果图片发生了改变，则需要重新计算
            isChangeBitmap = false
            matrix!!.reset()
            val bitmapWidth = originBitmap!!.width
            val bitmapHeight = originBitmap!!.height

            //如果图片的宽或高 大于控件视图的宽或高
            if (isFit || bitmapWidth > width || bitmapHeight > height) {
                val wRatio = width / bitmapWidth.toFloat()
                val hRatio = height / bitmapHeight.toFloat()
                if (wRatio < hRatio) { //图片宽铺满时
                    //以宽的比例进行等比例缩放保证图片能完全显示
                    matrix!!.postScale(wRatio, wRatio)
                    val translateY = (height - bitmapHeight * wRatio) / 2f
                    //进行平移，保证图片居中显示
                    matrix!!.postTranslate(0f, translateY)
                    currentTranslateX = 0f
                    currentTranslateY = translateY
                    initRatio = wRatio
                    realZoom = wRatio
                } else { //图片高铺满时
                    //以高的比例进行等比例缩放保证图片能完全显示
                    matrix!!.postScale(hRatio, hRatio)
                    val translateX = (width - bitmapWidth * hRatio) / 2f
                    //进行平移，保证图片居中显示
                    matrix!!.postTranslate(translateX, 0f)
                    currentTranslateX = translateX
                    currentTranslateY = 0f
                    initRatio = hRatio
                    realZoom = hRatio
                }
            } else {
                //图片的宽和高都小于控件视图的宽和高，则直接将图片居中显示
                val translateY = (height - bitmapHeight) / 2f
                val translateX = (width - bitmapWidth) / 2f
                matrix!!.postTranslate(translateX, translateY)
                currentTranslateX = translateX
                currentTranslateY = translateY
                initRatio = 1f
                realZoom = 1f
            }
            currentBitmapWidth = (bitmapWidth * initRatio).toInt()
            currentBitmapHeight = (bitmapHeight * initRatio).toInt()

            //创建绘图层和预览层画布相关
            drawingBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
            drawingCanvas = Canvas(drawingBitmap!!)
            previewBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
            previewCanvas = Canvas(previewBitmap!!)
            touchBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
            touchCanvas = Canvas(touchBitmap!!)
            if (onZoomListener != null) {
                onZoomListener!!.onZoomUpdate(realZoom, getZoom())
            }
        }
    }

    /**
     * 清除画布
     */
    fun clear() {
        drawingCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        drawList!!.clear()
        backupDrawList!!.clear()
        isHasUndo = false
        isHasRedo = false
        invalidate()
    }

    /**
     * 撤销一步
     */
    fun undo() {
        if (!drawList!!.isEmpty()) {
            drawingCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            //删除最后一步
            drawList!!.removeLast()
            //然后将画图记录重新画上去
            for (draw in drawList!!) {
                draw.draw(drawingCanvas)
            }
            invalidate()
            isRevoked = true
            isHasRedo = true
            //判断是否撤销到最后一步
            if (drawList!!.isEmpty()) {
                isHasUndo = false
            }
        } else {
            isHasUndo = false
        }
        if (onDrawListener != null) {
            onDrawListener!!.updateRevocationStatus()
        }
    }

    /**
     * 恢复一步（反撤销）
     */
    fun redo() {
        val backupSize = backupDrawList!!.size
        if (backupSize > 0) {
            val size = drawList!!.size
            if (size < backupSize) {
                drawingCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                if (size == 0) { //size为0时，表示已经撤销到最后一步了
                    drawList!!.add(backupDrawList!![0])
                } else { //恢复一步即可
                    isHasUndo = true
                    drawList!!.add(backupDrawList!![size])
                }
                //提前判断是否恢复到最后一步
                if (size + 1 == backupSize) {
                    isHasRedo = false
                }

                //然后将画图记录重新画上去
                for (draw in drawList!!) {
                    draw.draw(drawingCanvas)
                }
                invalidate()
            } else {
                isHasUndo = true
                isHasRedo = false
            }
            if (onDrawListener != null) {
                onDrawListener!!.updateRevocationStatus()
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) { //当布局发生改变时，记住控件视图的宽高
            width = getWidth()
            height = getHeight()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBitmap(canvas, matrix, isTouch)
    }

    /**
     * 通过代码进行绘制
     * 需要注意：绘制原始坐标点需要转换成矩阵上的坐标点；[.transfer]
     *
     * @param draw
     */
    fun draw(draw: Draw) {
        if (drawingCanvas != null) {
            if (draw.paint == null) { //如果画笔为空则根据当前配置自动创建画笔，如果draw 是绘制其他有依赖的需自己配置后再传进来
                draw.paint = createPaint(drawMode)
            }
            //进行绘制
            draw.draw(drawingCanvas)
            //将绘制记录保存起来，便于后续的撤销和恢复相关操作
            drawList!!.add(draw)
            if (isRevoked) {
                backupDrawList!!.clear()
                backupDrawList!!.addAll(drawList!!)
                isHasRedo = false
                isRevoked = false
            } else {
                backupDrawList!!.add(draw)
            }
            isHasUndo = true
            if (onDrawListener != null) {
                onDrawListener!!.onDraw(draw)
            }
            invalidate()
        }
    }

    /**
     * 将原始点坐标转换成画布矩阵上的点坐标，当通过代码调用[.draw]绘制时可能会用到此转换方法
     *
     * @param point
     */
    fun transfer(point: PointF) {
        point.x = (point.x - currentTranslateX) / realZoom
        point.y = (point.y - currentTranslateY) / realZoom
    }

    /**
     * 绘制图片
     *
     * @param canvas
     */
    private fun drawBitmap(canvas: Canvas, matrix: Matrix?, isTouch: Boolean) {
        updateImageBitmap()
        if (originBitmap != null) {
            canvas.drawBitmap(originBitmap!!, matrix!!, null)
        }
        if (drawingBitmap != null) {
            canvas.drawBitmap(drawingBitmap!!, matrix!!, null)
        }
        if (previewBitmap != null) {
            canvas.drawBitmap(previewBitmap!!, matrix!!, null)
        }
        if (isShowTouchPoint && isTouch && touchBitmap != null) {
            canvas.drawBitmap(touchBitmap!!, matrix!!, null)
        }
    }

    /**
     * 绘制触摸点
     *
     * @param x
     * @param y
     */
    private fun drawTouchPoint(x: Float, y: Float) {
        touchCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        if (drawMode == DrawMode.ERASER) {
            drawTouchPoint(touchCanvas, x, y, eraserStrokeWidth / 2 * touchPointRatio)
        } else {
            drawTouchPoint(touchCanvas, x, y, lineStrokeWidth / 2 * touchPointRatio)
        }
    }

    /**
     * 绘制缩放时触摸点
     *
     * @param x
     * @param y
     */
    private fun drawZoomPoint(x: Float, y: Float) {
        touchCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        drawTouchPoint(touchCanvas, x, y, zoomPointStrokeWidth / 2 * touchPointRatio)
    }

    /**
     * 绘制缩放时触摸点
     *
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     */
    private fun drawZoomPoint(x0: Float, y0: Float, x1: Float, y1: Float) {
        touchCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        drawTouchPoint(touchCanvas, x0, y0, zoomPointStrokeWidth)
        drawTouchPoint(touchCanvas, x1, y1, zoomPointStrokeWidth)
    }

    /**
     * 绘制触摸点
     *
     * @param canvas
     * @param x
     * @param y
     * @param radius
     */
    private fun drawTouchPoint(canvas: Canvas?, x: Float, y: Float, radius: Float) {
        canvas!!.drawCircle(x, y, radius, pointPaint!!)
    }

    /**
     * 创建画笔
     *
     * @param drawMode
     * @return
     */
    private fun createPaint(drawMode: Int): Paint {
        val paint = Paint()
        if (drawMode == DrawMode.ERASER) { // 当为橡皮擦模式时
            paint.style = Paint.Style.STROKE
            paint.color = Color.BLACK
            paint.strokeWidth = eraserStrokeWidth
            paint.isAntiAlias = false
            paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT))
        } else {
            paint.style = paintStyle
            paint.color = paintColor
            paint.strokeWidth = lineStrokeWidth
            paint.isAntiAlias = true
            paint.strokeJoin = Paint.Join.ROUND
            paint.strokeCap = paintStrokeCap
            if (isBlurMask) {
                paint.setMaskFilter(BlurMaskFilter(50f, BlurMaskFilter.Blur.SOLID))
            }
            if (paintShader != null) {
                paint.setShader(paintShader)
            }
            if (paintXfermode != null) {
                paint.setXfermode(paintXfermode)
            }
            if (pathEffect != null) {
                paint.setPathEffect(pathEffect)
            }
            if (blendMode != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                paint.blendMode = blendMode
            }
        }
        return paint
    }

    /**
     * 创建绘图对象
     *
     * @param drawMode
     * @return
     */
    private fun createDraw(@DrawMode drawMode: Int): Draw {
        val drawClass = drawMap!![drawMode]
        if (drawClass != null) {
            try {
                return drawClass.newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return object : Draw() {
            override fun draw(canvas: Canvas?) {}
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        isTouch = true
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> if (isDrawEnabled && event.pointerCount == 1) { //单指
                isZoom = false
                isDraw = false
                paint = createPaint(drawMode)
                draw = createDraw(drawMode)
                draw!!.paint = paint
                val x = event.x
                val y = event.y
                val ratioX = (x - currentTranslateX) / realZoom
                val ratioY = (y - currentTranslateY) / realZoom
                if (isShowTouchPoint) {
                    touchCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                }
                if (drawMode == DrawMode.ERASER) { //如果是橡皮擦模式，则直接使用绘制层画布
                    draw!!.actionDown(drawingCanvas, ratioX, ratioY)
                } else {
                    //绘制前先清空预览画布
                    previewCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                    draw!!.actionDown(previewCanvas, ratioX, ratioY)
                }
                lastX = event.x
                lastY = event.y
            }

            MotionEvent.ACTION_POINTER_DOWN -> if (isZoomEnabled && event.pointerCount >= 2) { //多指：判定为缩放
                isZoom = true
                val xPoint0 = event.getX(0)
                val yPoint0 = event.getY(0)
                val xPoint1 = event.getX(1)
                val yPoint1 = event.getY(1)
                // 两指之间的中心点
                lastCenterPointX = (xPoint0 + xPoint1) / 2
                lastCenterPointY = (yPoint0 + yPoint1) / 2
                // 两指之间的距离
                lastFingerDistance = distance(xPoint0, yPoint0, xPoint1, yPoint1)
                lastX = event.x
                lastY = event.y
            }

            MotionEvent.ACTION_MOVE -> if (isDrawEnabled && !isZoom) {
                val x = event.x
                val y = event.y
                if (Math.abs(lastX - x) > touchTolerance || Math.abs(lastY - y) > touchTolerance) {
                    isDraw = true
                    val ratioX = (x - currentTranslateX) / realZoom
                    val ratioY = (y - currentTranslateY) / realZoom
                    if (drawMode == DrawMode.ERASER) { //如果是橡皮擦模式，则直接使用绘制层画布
                        draw!!.actionMove(drawingCanvas, ratioX, ratioY)
                    } else {
                        //绘制前先清空预览画布
                        previewCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                        draw!!.actionMove(previewCanvas, ratioX, ratioY)
                    }
                    if (isShowTouchPoint) {
                        drawTouchPoint(ratioX, ratioY)
                    }
                    lastX = x
                    lastY = y
                }
            } else if (isZoomEnabled && isZoom) {
                processZoomEvent(event)
            }

            MotionEvent.ACTION_POINTER_UP -> if (isShowTouchPoint) {
                touchCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            }

            MotionEvent.ACTION_UP -> {
                if (isShowTouchPoint) {
                    touchCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                }
                if (draw != null && isDraw) {
                    val x = event.x
                    val y = event.y
                    val ratioX = (x - currentTranslateX) / realZoom
                    val ratioY = (y - currentTranslateY) / realZoom
                    if (drawMode == DrawMode.ERASER) { //如果是橡皮擦模式，则直接使用绘制层画布
                        draw!!.actionUp(drawingCanvas, ratioX, ratioY)
                    } else {
                        //绘制前先清空预览画布
                        previewCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                        draw!!.actionUp(previewCanvas, ratioX, ratioY)
                    }
                    //将之前绘制预览的结果绘制在图层上
                    draw!!.draw(drawingCanvas)
                    //将绘制记录保存起来，便于后续的撤销和恢复相关操作
                    drawList!!.add(draw!!)
                    if (isRevoked) {
                        backupDrawList!!.clear()
                        backupDrawList!!.addAll(drawList!!)
                        isHasRedo = false
                        isRevoked = false
                    } else {
                        backupDrawList!!.add(draw!!)
                    }
                    isHasUndo = true
                    if (onDrawListener != null) {
                        onDrawListener!!.onDraw(draw)
                        onDrawListener!!.updateRevocationStatus()
                    }
                }
                lastX = -1f
                lastY = -1f
                lastCenterPointX = -1f
                lastCenterPointY = -1f
                isZoom = false
                isTouch = false
                isDraw = false
            }
        }
        invalidate()
        return true
    }

    /**
     * 处理缩放事件
     *
     * @param event
     */
    private fun processZoomEvent(event: MotionEvent) {
        val pointCount = event.pointerCount
        if (pointCount >= 2) { //多指时，计算缩放和平移
            val xPoint0 = event.getX(0)
            val yPoint0 = event.getY(0)
            val xPoint1 = event.getX(1)
            val yPoint1 = event.getY(1)

            // 两指之间的中心点
            val centerPointX = (xPoint0 + xPoint1) / 2
            val centerPointY = (yPoint0 + yPoint1) / 2
            // 两指之间的距离
            val fingerDistance = distance(xPoint0, yPoint0, xPoint1, yPoint1)
            scaledRatio = fingerDistance / lastFingerDistance

            //缩放比例
            if (scaledRatio < 1f) { //两点之间的距离小于上一次，表示缩小
                val minRatio = if (isFit) initRatio else minZoom
                if (realZoom > minRatio) {
                    realZoom = realZoom * scaledRatio
                    //边界处理，currentRatio 最小不得小于 minRatio
                    if (realZoom < minRatio) {
                        realZoom = minRatio
                    }
                }
            } else { //反之，表示放大
                val maxRatio = if (isFit) Math.max(initRatio, maxZoom) else maxZoom
                if (realZoom < maxZoom) {
                    realZoom = realZoom * scaledRatio
                    //边界处理，currentRatio 最大不得大于 maxRatio
                    if (realZoom > maxRatio) {
                        realZoom = maxRatio
                    }
                }
            }

            //平移距离
            if (lastCenterPointX != -1f && lastCenterPointY != -1f) {
                movedDistanceX = centerPointX - lastCenterPointX
                movedDistanceY = centerPointY - lastCenterPointY
            }

            //更新最后一次记录的值
            lastCenterPointX = centerPointX
            lastCenterPointY = centerPointY
            lastFingerDistance = fingerDistance
        } else { //单指时，只计算平移
            movedDistanceX = event.x - lastX
            movedDistanceY = event.y - lastY
        }

        // 进行边界检查，不允许将图片拖出边界
        if (currentTranslateX + movedDistanceX > 0) {
            movedDistanceX = 0f
        } else if (width - (currentTranslateX + movedDistanceX) > currentBitmapWidth) {
            movedDistanceX = 0f
        }
        if (currentTranslateY + movedDistanceY > 0) {
            movedDistanceY = 0f
        } else if (height - (currentTranslateY + movedDistanceY) > currentBitmapHeight) {
            movedDistanceY = 0f
        }
        //进行矩阵缩放
        zoom(lastCenterPointX, lastCenterPointY)

        //如果需要显示触摸点，则进行显示
        if (isShowTouchPoint) {
            if (pointCount >= 2) {
                val xPoint0 = event.getX(0)
                val yPoint0 = event.getY(0)
                val xPoint1 = event.getX(1)
                val yPoint1 = event.getY(1)
                val ratioX0 = (xPoint0 - currentTranslateX) / realZoom
                val ratioY0 = (yPoint0 - currentTranslateY) / realZoom
                val ratioX1 = (xPoint1 - currentTranslateX) / realZoom
                val ratioY1 = (yPoint1 - currentTranslateY) / realZoom
                drawZoomPoint(ratioX0, ratioY0, ratioX1, ratioY1)
            } else {
                val xPoint0 = event.getX(0)
                val yPoint0 = event.getY(0)
                val ratioX0 = (xPoint0 - currentTranslateX) / realZoom
                val ratioY0 = (yPoint0 - currentTranslateY) / realZoom
                drawZoomPoint(ratioX0, ratioY0)
            }
        }
        lastX = event.x
        lastY = event.y
    }

    /**
     * 矩阵缩放
     *
     * @param centerPointX
     * @param centerPointY
     */
    private fun zoom(centerPointX: Float, centerPointY: Float) {
        matrix!!.reset()
        // 将图片按总缩放比例进行缩放
        matrix!!.postScale(realZoom, realZoom)
        val scaledWidth = originBitmap!!.width * realZoom
        val scaledHeight = originBitmap!!.height * realZoom
        var translateX: Float
        var translateY: Float

        // 如果当前图片宽度小于控件视图宽度，则按控件视图中心的横坐标进行水平缩放。否则按两指的中心点的横坐标进行水平缩放
        if (currentBitmapWidth < width) {
            translateX = (width - scaledWidth) / 2f
        } else {
            translateX = currentTranslateX * scaledRatio + centerPointX * (1 - scaledRatio)
            translateX = translateX + movedDistanceX //加上中心点移动距离
            // 进行边界检查，保证图片缩放后在水平方向上不会偏移出控件视图
            if (translateX > 0) {
                translateX = 0f
            } else if (width - translateX > scaledWidth) {
                translateX = width - scaledWidth
            }
        }
        // 如果当前图片高度小于控件视图高度，则按控件视图中心的纵坐标进行垂直缩放。否则按两指的中心点的纵坐标进行垂直缩放
        if (currentBitmapHeight < height) {
            translateY = (height - scaledHeight) / 2f
        } else {
            translateY = currentTranslateY * scaledRatio + centerPointY * (1 - scaledRatio)
            translateY = translateY + movedDistanceY //加上中心点移动距离
            // 进行边界检查，保证图片缩放后在垂直方向上不会偏移出控件视图
            if (translateY > 0) {
                translateY = 0f
            } else if (height - translateY > scaledHeight) {
                translateY = height - scaledHeight
            }
        }
        // 缩放后对图片进行偏移，以保证缩放后中心点位置不变
        matrix!!.postTranslate(translateX, translateY)
        currentTranslateX = translateX
        currentTranslateY = translateY
        currentBitmapWidth = scaledWidth.toInt()
        currentBitmapHeight = scaledHeight.toInt()
        if (onZoomListener != null) {
            onZoomListener!!.onZoomUpdate(realZoom, getZoom())
        }
    }

    /**
     * 计算两点之间的距离
     *
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @return
     */
    private fun distance(x0: Float, y0: Float, x1: Float, y1: Float): Float {
        val disX = Math.abs(x0 - x1)
        val disY = Math.abs(y0 - y1)
        return Math.sqrt((disX * disX + disY * disY).toDouble()).toFloat()
    }

    val bitmapWidth: Int
        /**
         * 获取图片的宽度
         *
         * @return
         */
        get() = if (originBitmap != null) {
            originBitmap!!.width
        } else width
    val bitmapHeight: Int
        /**
         * 获取图片的高度
         *
         * @return
         */
        get() = if (originBitmap != null) {
            originBitmap!!.height
        } else height

    /**
     * 设置是否为圆角
     */
    fun setRound(paintStrokeCap: Cap) {
        this.paintStrokeCap = paintStrokeCap
        isBlurMask = false
    }

    /**
     * 设置是否为模糊
     */
    fun setBlurMask(isBlurMask: Boolean) {
        this.isBlurMask = isBlurMask
    }

    /**
     * 设置画笔线条描边宽度
     *
     * @param lineStrokeWidth
     */
    fun setLineStrokeWidth(lineStrokeWidth: Float) {
        this.lineStrokeWidth = lineStrokeWidth
    }

    /**
     * 设置橡皮擦描边宽度
     *
     * @param eraserStrokeWidth
     */
    fun setEraserStrokeWidth(eraserStrokeWidth: Float) {
        this.eraserStrokeWidth = eraserStrokeWidth
    }

    /**
     * 设置缩放点描边宽度
     *
     * @param zoomPointStrokeWidth
     */
    fun setZoomPointStrokeWidth(zoomPointStrokeWidth: Float) {
        this.zoomPointStrokeWidth = zoomPointStrokeWidth
    }

    /**
     * 相对的变焦倍数
     *
     * @return
     */
    fun getZoom(): Float {
        return realZoom / initRatio
    }

    /**
     * 设置需要位置的位图锚点是否居中
     *
     * @param drawBitmapAnchorCenter
     */
    fun setDrawBitmapAnchorCenter(drawBitmapAnchorCenter: Boolean) {
        isDrawBitmapAnchorCenter = drawBitmapAnchorCenter
    }

    /**
     * 缩放监听
     *
     * @param onZoomListener
     */
    fun setOnZoomListener(onZoomListener: OnZoomListener?) {
        this.onZoomListener = onZoomListener
    }

    /**
     * 缩放监听
     */
    interface OnZoomListener {
        /**
         * 缩放更新
         *
         * @param realZoom 图片的真实的变焦倍数
         * @param zoom     图片的相对变焦倍数
         */
        fun onZoomUpdate(realZoom: Float, zoom: Float)
    }

    /**
     * 绘图监听
     *
     * @param onDrawListener
     */
    fun setOnDrawListener(onDrawListener: OnDrawListener?) {
        this.onDrawListener = onDrawListener
    }

    /**
     * 绘图监听
     */
    interface OnDrawListener {
        /**
         * 绘制监听
         *
         * @param draw
         */
        fun onDraw(draw: Draw?)

        //更新撤销状态
        fun updateRevocationStatus()
    }

    companion object {
        /**
         * 触摸时默认允许的容差值
         */
        private const val TOUCH_TOLERANCE = 4f

        /**
         * 触摸时默认点的比例
         */
        private const val TOUCH_POINT_RATIO = 1.2f
    }
}
