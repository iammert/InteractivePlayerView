package co.mobiwise.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by mertsimsek on 14/08/15.
 */
public class InteractivePlayerView extends View{

    /**
     * Action 1 drawable,paint, bitmap and isSelected values
     */
    private Drawable drawableSelectedAction2;

    private Drawable drawableUnselectedAction2;

    private boolean isAction2Selected = false;

    private Bitmap mBitmapSelectedAction2;

    private Bitmap mBitmapUnselectedAction2;

    private Region middleActionRegion;

    /**
     * Action 2 drawable,paint, bitmap and isSelected values
     */
    private Drawable drawableSelectedAction1;

    private Drawable drawableUnselectedAction1;

    private boolean isAction1Selected = false;

    private Bitmap mBitmapSelectedAction1;

    private Bitmap mBitmapUnselectedAction1;

    private Region leftActionRegion;

    /**
     * Action 3 drawable,paint, bitmap and isSelected values
     */
    private Drawable drawableSelectedAction3;

    private Drawable drawableUnselectedAction3;

    private boolean isAction3Selected = false;

    private Bitmap mBitmapSelectedAction3;

    private Bitmap mBitmapUnselectedAction3;

    private Region rightActionRegion;

    private Paint mActionPaint;

    private OnActionClickedListener onActionClickedListener;

    /**
     * empty progress paint
     */
    private Paint mPaintEmptyProgress;

    /**
     * loaded progress paint
     */
    private Paint mPaintLoadedProgress;

    /**
     * empty progress color
     */
    private int mEmptyProgressColor;

    /**
     * loaded progress color
     */
    private int mLoadedProgressColor;

    /**
     * Progress toggle
     */
    private Paint mPaintProgressToggle;

    /**
     * Progress toggle radius
     */
    private float mRadiusToggle;

    /**
     * Default empty progress color
     */
    private static final int COLOR_EMPTY_PROGRESS_DEFAULT = 0xAAFFFFFF;

    /**
     * Default empty progress color
     */
    private static final int COLOR_LOADED_PROGRESS_DEFAULT = 0xFFF44336;

    /**
     * progress rectf
     */
    private RectF mProgressRectF;

    /**
     * Duration paint for duration text
     */
    private Paint mDurationPaint;

    /**
     * Paint object for making cover
     * photo little bit darker.
     */
    private Paint mCoverFrontPaint;

    /**
     * Cover image paint
     */
    private Paint mCoverPaint;

    /**
     * Cover bitmap
     */
    private Bitmap mBitmapCover;

    /**
     * Cover shader to make it circle
     */
    private BitmapShader mBitmapShader;

    /**
     * Scale image to existing size
     */
    private float mCoverScale;

    /**
     * Image height
     */
    private int mHeight;

    /**
     * Image width
     */
    private int mWidth;

    /**
     * Image center X
     */
    private float mCenterX;

    /**
     * Image center Y
     */
    private float mCenterY;

    /**
     * Cover Image radius
     */
    private float mCoverRadius;

    /**
     * %15 transparency black color for making
     * cover image little bit darker
     */
    private final static int COLOR_BLACK_TRANSPARENT = 0x26000000;

    /**
     * Default color code for cover
     */
    private int mCoverColor = Color.GRAY;

    /**
     * Typeface for time
     */
    private Typeface mTypeFaceRobotoThin;

    /**
     * Duration text size
     */
    private int sizeDurationText = 180;

    /**
     * Music duration
     */
    private int mDurationSecondsMax = 0;

    /**
     * Music current duration
     */
    private int mDurationSecondsCurrent = 0;

    /**
     * duration on string type
     */
    private String mDurationText = "";

    /**
     * This will be used to calculate text width.
     */
    private Rect mRectDuration;

    /**
     * count duration handler
     */
    private Handler mHandlerDuration;

    /**
     * count duration runnable
     */
    private Runnable mRunnableDuration;

    /**
     * Is music playing
     */
    private boolean isPlaying = false;

    /**
     * One Second
     */
    private static final int ONE_SECOND = 1000;


    /**
     * Constructors
     * @param context
     */
    public InteractivePlayerView(Context context) {
        super(context);
        init(context, null);
    }

    public InteractivePlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InteractivePlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InteractivePlayerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * Inıtıalizes all paint, drawable, bitmap objects.
     * Object initializations must be defined here because this method
     * is called only once.(If you want smooth view, Stay away from onDraw() :))
     * @param context
     * @param attrs
     */
    public void init(Context context, AttributeSet attrs){

        //Get Image resource from xml
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.interactiveplayerview);
        Drawable mDrawableCover = a.getDrawable(R.styleable.interactiveplayerview_imageCover);
        if (mDrawableCover != null)
            mBitmapCover = drawableToBitmap(mDrawableCover);

        sizeDurationText = a.getDimensionPixelSize(R.styleable.interactiveplayerview_durationSize, sizeDurationText);

        mEmptyProgressColor = a.getColor(R.styleable.interactiveplayerview_emptyColor, COLOR_EMPTY_PROGRESS_DEFAULT);
        mLoadedProgressColor = a.getColor(R.styleable.interactiveplayerview_loadedColor, COLOR_LOADED_PROGRESS_DEFAULT);

        /**
         * Action images initialization
         */
        //Action 2
        drawableSelectedAction2 = a.getDrawable(R.styleable.interactiveplayerview_selectedAction2);
        drawableUnselectedAction2 = a.getDrawable(R.styleable.interactiveplayerview_unselectedAction2);

        if(drawableSelectedAction2 != null)
            mBitmapSelectedAction2 = drawableToBitmap(drawableSelectedAction2);
        if(drawableUnselectedAction2 != null)
            mBitmapUnselectedAction2 = drawableToBitmap(drawableUnselectedAction2);

        //Action 1
        drawableSelectedAction1 = a.getDrawable(R.styleable.interactiveplayerview_selectedAction1);
        drawableUnselectedAction1 = a.getDrawable(R.styleable.interactiveplayerview_unselectedAction1);

        if(drawableSelectedAction1 != null)
            mBitmapSelectedAction1 = drawableToBitmap(drawableSelectedAction1);
        if(drawableUnselectedAction1 != null)
            mBitmapUnselectedAction1 = drawableToBitmap(drawableUnselectedAction1);

        //Action 3
        drawableSelectedAction3 = a.getDrawable(R.styleable.interactiveplayerview_selectedAction3);
        drawableUnselectedAction3 = a.getDrawable(R.styleable.interactiveplayerview_unselectedAction3);

        if(drawableSelectedAction3 != null)
            mBitmapSelectedAction3 = drawableToBitmap(drawableSelectedAction3);
        if(drawableUnselectedAction3 != null)
            mBitmapUnselectedAction3 = drawableToBitmap(drawableUnselectedAction3);

        a.recycle();

        mTypeFaceRobotoThin = Typeface.createFromAsset(context.getAssets(), "robotothin.ttf");

        /**
         * Paint initialization
         */
        mCoverFrontPaint = new Paint();
        mCoverFrontPaint.setAntiAlias(true);
        mCoverFrontPaint.setStyle(Paint.Style.FILL);
        mCoverFrontPaint.setColor(COLOR_BLACK_TRANSPARENT);

        mDurationPaint = new Paint();
        mDurationPaint.setAntiAlias(true);
        mDurationPaint.setColor(Color.WHITE);
        mDurationPaint.setTypeface(mTypeFaceRobotoThin);
        mDurationPaint.setTextSize(sizeDurationText);

        mActionPaint = new Paint();
        mActionPaint.setAntiAlias(true);

        mPaintEmptyProgress = new Paint();
        mPaintEmptyProgress.setAntiAlias(true);
        mPaintEmptyProgress.setColor(mEmptyProgressColor);
        mPaintEmptyProgress.setStyle(Paint.Style.STROKE);
        mPaintEmptyProgress.setStrokeWidth(10.0f);

        mPaintLoadedProgress = new Paint();
        mPaintLoadedProgress.setAntiAlias(true);
        mPaintLoadedProgress.setColor(mLoadedProgressColor);
        mPaintLoadedProgress.setStyle(Paint.Style.STROKE);
        mPaintLoadedProgress.setStrokeWidth(10.0f);

        mPaintProgressToggle = new Paint();
        mPaintProgressToggle.setAntiAlias(true);
        mPaintProgressToggle.setColor(mLoadedProgressColor);
        mPaintProgressToggle.setStyle(Paint.Style.FILL);

        mRectDuration = new Rect();
        mProgressRectF = new RectF();

        startDurationRunnable();

    }

    /**
     * duration seconds and minutes will be counting while isPlaying value
     * is true and current seconds is less than max.
     */
    private void startDurationRunnable(){
        mHandlerDuration = new Handler();

        mRunnableDuration = new Runnable() {
            @Override
            public void run() {
                if(isPlaying && mDurationSecondsMax > mDurationSecondsCurrent){
                    mDurationSecondsCurrent++;
                    mHandlerDuration.postDelayed(mRunnableDuration, ONE_SECOND);
                    postInvalidate();
                }
                else if(mDurationSecondsCurrent == mDurationSecondsMax){
                    mDurationSecondsCurrent = 0;
                    isPlaying = false;
                    postInvalidate();
                }
            }
        };
    }

    /**
     * Calculate image width, height, center, radius etc. values.
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        int minSide = Math.min(mWidth, mHeight);
        mWidth = minSide;
        mHeight = minSide;

        this.setMeasuredDimension(mWidth, mHeight);

        mCenterX = mWidth / 2f;
        mCenterY = mHeight / 2f;

        mCoverRadius = minSide / 2.3f;

        mRadiusToggle = mWidth / 40.0f;

        sizeDurationText = mHeight / 5;
        mDurationPaint.setTextSize(sizeDurationText);

        createShader();

        if(mBitmapUnselectedAction1 != null && mBitmapSelectedAction1 != null){
            mBitmapSelectedAction1 = scaleBitmap(mBitmapSelectedAction1, (int) (mWidth / 13.0f), (int) (mHeight / 13.0f));
            mBitmapUnselectedAction1 = scaleBitmap(mBitmapUnselectedAction1, (int)(mWidth / 13.0f), (int)(mHeight / 13.0f));
        }

        if(mBitmapUnselectedAction2 != null && mBitmapSelectedAction2 != null){
            mBitmapSelectedAction2 = scaleBitmap(mBitmapSelectedAction2, (int) (mWidth / 13.0f), (int) (mHeight / 13.0f));
            mBitmapUnselectedAction2 = scaleBitmap(mBitmapUnselectedAction2, (int)(mWidth / 13.0f), (int)(mHeight / 13.0f));
        }

        if(mBitmapUnselectedAction3 != null && mBitmapSelectedAction3 != null){
            mBitmapSelectedAction3 = scaleBitmap(mBitmapSelectedAction3, (int) (mWidth / 13.0f), (int) (mHeight / 13.0f));
            mBitmapUnselectedAction3 = scaleBitmap(mBitmapUnselectedAction3, (int) (mWidth / 13.0f), (int) (mHeight / 13.0f));
        }

        middleActionRegion = new Region(
                (int)(mCenterX - (mCenterX / 13.0f)),
                (int)(mCenterY + (mCenterY / 3.0f) - (mCenterY / 13.0f)),
                (int)(mCenterX + (mCenterX / 13.0f)),
                (int)(mCenterY + (mCenterY / 3.0f) + (mCenterY / 13.0f)));

        leftActionRegion = new Region(
                (int)(mCenterX - (5 * (mCenterX / 13.0f))),
                (int)(mCenterY + (mCenterY / 3.0f) - (mCenterY / 13.0f)),
                (int)(mCenterX - (3 * (mCenterX / 13.0f))),
                (int)(mCenterY + (mCenterY / 3.0f) + (mCenterY / 13.0f)));

        rightActionRegion = new Region(
                (int)(mCenterX + (3 * (mCenterX / 13.0f))),
                (int)(mCenterY + (mCenterY / 3.0f) - (mCenterY / 13.0f)),
                (int)(mCenterX + (5 * (mCenterX / 13.0f))),
                (int)(mCenterY + (mCenterY / 3.0f) + (mCenterY / 13.0f)));

        mProgressRectF.set(20.0f, 20.0f, mWidth - 20.0f, mHeight - 20.0f);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * What magic happens.
     * 1- draw image cover in circle
     * 2- draw black shadow on it to make seconds and icons more visible
     * 3- draw duration center of image.
     * 4- put icon/s on it
     * 5- draw progress
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmapShader == null)
            return;

        /**
         * Draw cover image and transparent black cover
         */
        canvas.drawCircle(mCenterX, mCenterY, mCoverRadius, mCoverPaint);
        canvas.drawCircle(mCenterX, mCenterY, mCoverRadius, mCoverFrontPaint);

        /**
         * Draw duration text in the middle of view
         */
        mDurationText = secondsToTime(mDurationSecondsCurrent);
        mDurationPaint.getTextBounds(mDurationText, 0, mDurationText.length(), mRectDuration);
        canvas.drawText(mDurationText,
                (mCenterX - (mRectDuration.width() / 2.0f)),
                (mCenterY + (mRectDuration.height() / 2.0f)),
                mDurationPaint);

        /**
         * Draw action images
         */
        if(mBitmapUnselectedAction1 != null && mBitmapSelectedAction1 != null){
            canvas.drawBitmap(isAction1Selected ? mBitmapSelectedAction1 : mBitmapUnselectedAction1,
                    (mCenterX - (5 * (mCenterX / 13.0f))),
                    mCenterY + (mCenterY / 3.0f) - (mCenterY / 13.0f),
                    mActionPaint);
        }

        if(mBitmapUnselectedAction2 != null && mBitmapSelectedAction2 != null){
            canvas.drawBitmap(
                    isAction2Selected ? mBitmapSelectedAction2 : mBitmapUnselectedAction2,
                    mCenterX - (mCenterX / 13.0f),
                    mCenterY + (mCenterY / 3.0f) - (mCenterY / 13.0f),
                    mActionPaint);
        }

        if(mBitmapUnselectedAction3 != null && mBitmapSelectedAction3 != null){
            canvas.drawBitmap(isAction3Selected ? mBitmapSelectedAction3 : mBitmapUnselectedAction3,
                    (int)(mCenterX + (3  * (mCenterX / 13.0f))),
                    mCenterY + (mCenterY / 3.0f) - (mCenterY / 13.0f),
                    mActionPaint);
        }

        /**
         * Draw progress
         */
        canvas.drawArc(mProgressRectF, 0, 360, false, mPaintEmptyProgress);
        canvas.drawArc(mProgressRectF, 270, calculatePastProgress(), false, mPaintLoadedProgress);
        canvas.drawCircle(
                (float) (mCenterX + ((mCenterX - 20.0f) * Math.cos(Math.toRadians(calculatePastProgress() - 90)))),
                (float) (mCenterY + ((mCenterX - 20.0f) * Math.sin(Math.toRadians(calculatePastProgress() - 90)))),
                mRadiusToggle,
                mPaintProgressToggle);
    }

    /**
     * We need to convert drawable (which we get from attributes) to bitmap
     * to prepare if for BitmapShader
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int mWidth, int mHeight){
        return Bitmap.createScaledBitmap(bitmap, mWidth, mHeight, false);
    }

    /**
     * Create shader and set shader to mPaintCover
     */
    private void createShader() {

        if (mWidth == 0)
            return;

        //if mBitmapCover is null then create default colored cover
        if (mBitmapCover == null) {
            mBitmapCover = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            mBitmapCover.eraseColor(mCoverColor);
        }

        mCoverScale = ((float) mWidth) / (float) mBitmapCover.getWidth();

        mBitmapCover = Bitmap.createScaledBitmap(mBitmapCover,
                (int) (mBitmapCover.getWidth() * mCoverScale),
                (int) (mBitmapCover.getHeight() * mCoverScale),
                true);

        mBitmapShader = new BitmapShader(mBitmapCover, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mCoverPaint = new Paint();
        mCoverPaint.setAntiAlias(true);
        mCoverPaint.setShader(mBitmapShader);

    }

    /**
     * set cover image resource
     *
     * @param coverDrawable
     */
    public void setCoverDrawable(int coverDrawable) {
        Drawable drawable = getContext().getResources().getDrawable(coverDrawable);
        mBitmapCover = drawableToBitmap(drawable);
        createShader();
        postInvalidate();
    }

    public void setActionOneImage(int selectedImage, int unselectedImage){
        Drawable selectedDrawable = getContext().getDrawable(selectedImage);
        Drawable unselectedDrawable = getContext().getDrawable(unselectedImage);
        mBitmapSelectedAction1 = drawableToBitmap(selectedDrawable);
        mBitmapUnselectedAction1 = drawableToBitmap(unselectedDrawable);
        if(mWidth > 0 || mHeight >0){
            mBitmapSelectedAction1 = scaleBitmap(mBitmapSelectedAction1, (int) (mWidth / 13.0f), (int) (mHeight / 13.0f));
            mBitmapUnselectedAction1 = scaleBitmap(mBitmapUnselectedAction1, (int) (mWidth / 13.0f), (int) (mHeight / 13.0f));
            postInvalidate();
        }
    }

    public void setActionTwoImage(int selectedImage, int unselectedImage){
        Drawable selectedDrawable = getContext().getDrawable(selectedImage);
        Drawable unselectedDrawable = getContext().getDrawable(unselectedImage);
        mBitmapSelectedAction2 = drawableToBitmap(selectedDrawable);
        mBitmapUnselectedAction2 = drawableToBitmap(unselectedDrawable);
        if(mWidth > 0 || mHeight >0){
            mBitmapSelectedAction2 = scaleBitmap(mBitmapSelectedAction2, (int) (mWidth / 13.0f), (int) (mHeight / 13.0f));
            mBitmapUnselectedAction2 = scaleBitmap(mBitmapUnselectedAction2, (int) (mWidth / 13.0f), (int) (mHeight / 13.0f));
            postInvalidate();
        }
    }

    public void setActionThreeImage(int selectedImage, int unselectedImage){
        Drawable selectedDrawable = getContext().getDrawable(selectedImage);
        Drawable unselectedDrawable = getContext().getDrawable(unselectedImage);
        mBitmapSelectedAction3 = drawableToBitmap(selectedDrawable);
        mBitmapUnselectedAction3 = drawableToBitmap(unselectedDrawable);
        if(mWidth > 0 || mHeight >0){
            mBitmapSelectedAction3 = scaleBitmap(mBitmapSelectedAction3, (int) (mWidth / 13.0f), (int) (mHeight / 13.0f));
            mBitmapUnselectedAction3 = scaleBitmap(mBitmapUnselectedAction3, (int) (mWidth / 13.0f), (int) (mHeight / 13.0f));
            postInvalidate();
        }
    }

    /**
     * gets image URL and load it to cover image.It uses Picasso Library.
     *
     * @param imageUrl
     */
    public void setCoverURL(String imageUrl) {
        Picasso.with(getContext()).load(imageUrl).into(target);
    }

    /**
     * When picasso load into target. Overrider methods are called.
     * Invalidate view onBitmapLoaded.
     */
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mBitmapCover = bitmap;
            createShader();
            postInvalidate();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    public void setMax(int mDurationSecondsMax){
        this.mDurationSecondsMax = mDurationSecondsMax;
        postInvalidate();
    }

    /**
     * Convert seconds to time
     * @param seconds
     * @return
     */
    private String secondsToTime(int seconds){
        String time = "";

        String minutesText = String.valueOf(seconds / 60);
        if(minutesText.length() == 1)
            minutesText = "0" + minutesText;

        String secondsText = String.valueOf(seconds % 60);
        if (secondsText.length() == 1)
            secondsText = "0" + secondsText;

        time = minutesText + ":" + secondsText;

        return time;
    }

    public void start(){
        isPlaying = true;
        mHandlerDuration.postDelayed(mRunnableDuration, ONE_SECOND);
    }

    public void stop() {
        isPlaying= false;
        mHandlerDuration.removeCallbacks(mRunnableDuration);
    }

    public boolean isPlaying(){
        return isPlaying;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                return true;
            }
            case MotionEvent.ACTION_UP: {
                if (onActionClickedListener != null){
                    if(leftActionRegion.contains((int)x, (int) y)){
                        onActionClickedListener.onActionClicked(1);
                        isAction1Selected = !isAction1Selected;
                        postInvalidate();
                    }
                    else if (middleActionRegion.contains((int) x, (int) y)) {
                        onActionClickedListener.onActionClicked(2);
                        isAction2Selected = !isAction2Selected;
                        postInvalidate();
                    }
                    else if(rightActionRegion.contains((int)x, (int) y)){
                        onActionClickedListener.onActionClicked(3);
                        isAction3Selected = !isAction3Selected;
                        postInvalidate();
                    }
                }
            }
            break;
        }

        return super.onTouchEvent(event);
    }

    public void setOnActionClickedListener(OnActionClickedListener onActionClickedListener){
        this.onActionClickedListener = onActionClickedListener;
    }

    public void setAction1Selected(boolean isAction1Selected){
        this.isAction1Selected = isAction1Selected;
        postInvalidate();
    }

    public boolean isAction1Selected(){
        return isAction1Selected;
    }

    public void setAction2Selected(boolean isAction2Selected){
        this.isAction2Selected = isAction2Selected;
        postInvalidate();
    }

    public boolean isAction2Selected(){
        return isAction2Selected;
    }

    public void setAction3Selected(boolean isAction3Selected){
        this.isAction3Selected = isAction3Selected;
        postInvalidate();
    }

    public boolean isAction3Selected(){
        return isAction3Selected;
    }

    private int calculatePastProgress(){
        return (360 * mDurationSecondsCurrent) / mDurationSecondsMax;
    }

    public void setProgress(int progressSeconds){
        mDurationSecondsCurrent = progressSeconds;
        postInvalidate();
    }

    public int getProgress(){
        return mDurationSecondsCurrent;
    }

    public void setProgressLoadedColor(int mLoadedProgressColor){
        this.mLoadedProgressColor = mLoadedProgressColor;
        mPaintLoadedProgress.setColor(mLoadedProgressColor);
        mPaintProgressToggle.setColor(mLoadedProgressColor);
        postInvalidate();
    }

    public void setProgressEmptyColor(int mEmptyProgressColor){
        this.mEmptyProgressColor = mEmptyProgressColor;
        mPaintEmptyProgress.setColor(mEmptyProgressColor);
        postInvalidate();
    }
}
