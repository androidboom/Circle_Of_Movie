package com.example.liubo.world_of_movie.video;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.liubo.world_of_movie.R;

import java.util.Formatter;
import java.util.Locale;


public class WylMediaControl extends MediaController {
	private MediaPlayerControl mPlayer;
	private Context mContext;
	private View mAnchor;
	private View mRoot;
	private WindowManager mWindowManager;
	private Window mWindow;
	private View mDecor;
	private WindowManager.LayoutParams mDecorLayoutParams;
	private ProgressBar mProgress;
	private TextView mEndTime, mCurrentTime;
	private boolean mShowing;
	private boolean mDragging;
	private static final int sDefaultTimeout = 3000;
	private static final int FADE_OUT = 1;
	private static final int SHOW_PROGRESS = 2;
	private boolean mUseFastForward;
	private boolean mFromXml;
	private boolean mListenersSet;
	private OnClickListener mNextListener, mPrevListener;
	StringBuilder mFormatBuilder;
	Formatter mFormatter;
	private ImageView mPauseButton;
//	private ImageButton mPauseButton;
	private ImageButton mFfwdButton;
	private ImageButton mRewButton;
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	private ImageView mShareBtn;

	public WylMediaControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		mRoot = this;
		mContext = context;
		mUseFastForward = true;
		mFromXml = true;
	}

	@Override
	public void onFinishInflate() {
		if (mRoot != null)
			initControllerView(mRoot);
	}

	public WylMediaControl(Context context, boolean useFastForward) {
		super(context);
		mContext = context;
		mUseFastForward = useFastForward;
		initFloatingWindowLayout();
		// initFloatingWindow();
	}

	public WylMediaControl(Context context) {
		this(context, true);
	}

	// private void initFloatingWindow() {
	// mWindowManager =
	// (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
	// mWindow = PolicyManager.makeNewWindow(mContext);
	// mWindow.setWindowManager(mWindowManager, null, null);
	// mWindow.requestFeature(Window.FEATURE_NO_TITLE);
	// mDecor = mWindow.getDecorView();
	// mDecor.setOnTouchListener(mTouchListener);
	// mWindow.setContentView(this);
	// mWindow.setBackgroundDrawableResource(android.R.color.transparent);
	//
	// // While the media controller is up, the volume control keys should
	// // affect the media stream type
	// mWindow.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	//
	// setFocusable(true);
	// setFocusableInTouchMode(true);
	// setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
	// requestFocus();
	// }

	// Allocate and initialize the static parts of mDecorLayoutParams. Must
	// also call updateFloatingWindowLayout() to fill in the dynamic parts
	// (y and width) before mDecorLayoutParams can be used.
	private void initFloatingWindowLayout() {
		mDecorLayoutParams = new WindowManager.LayoutParams();
		WindowManager.LayoutParams p = mDecorLayoutParams;
		p.gravity = Gravity.TOP | Gravity.LEFT;
		p.height = LayoutParams.WRAP_CONTENT;
		p.x = 0;
		p.format = PixelFormat.TRANSLUCENT;
		p.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
		p.flags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
				| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_SPLIT_TOUCH;
		p.token = null;
		p.windowAnimations = 0; // android.R.style.DropDownAnimationDown;
	}

	// Update the dynamic parts of mDecorLayoutParams
	// Must be called with mAnchor != NULL.
	private void updateFloatingWindowLayout() {
		int[] anchorPos = new int[2];
		mAnchor.getLocationOnScreen(anchorPos);

		// we need to know the size of the controller so we can properly
		// position it
		// within its space
		mDecor.measure(MeasureSpec.makeMeasureSpec(mAnchor.getWidth(),
				MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(
				mAnchor.getHeight(), MeasureSpec.AT_MOST));

		WindowManager.LayoutParams p = mDecorLayoutParams;
		p.width = mAnchor.getWidth();
		p.x = anchorPos[0] + (mAnchor.getWidth() - p.width) / 2;
		p.y = anchorPos[1] + mAnchor.getHeight() - mDecor.getMeasuredHeight();
	}

	// This is called whenever mAnchor's layout bound changes
	private OnLayoutChangeListener mLayoutChangeListener = new OnLayoutChangeListener() {
		public void onLayoutChange(View v, int left, int top, int right,
                                   int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
			updateFloatingWindowLayout();
			if (mShowing) {
				mWindowManager.updateViewLayout(mDecor, mDecorLayoutParams);
			}
		}
	};

	private OnTouchListener mTouchListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (mShowing) {
					hide();
				}
			}
			return false;
		}
	};

	@Override
	public void setMediaPlayer(MediaPlayerControl player) {
		mPlayer = player;
		updatePausePlay();
	}
	
	/**
	 * Set the view that acts as the anchor for the control view. This can for
	 * example be a VideoView, or your Activity's main view. When VideoView
	 * calls this method, it will use the VideoView's parent as the anchor.
	 * 
	 * @param view
	 *            The view to which to anchor the controller when it is visible.
	 */
	public void setAnchorView(View view) {
//		if (mAnchor != null) {
//			mAnchor.removeOnLayoutChangeListener(mLayoutChangeListener);
//		}
		mAnchor = view;
//		if (mAnchor != null) {
//			mAnchor.addOnLayoutChangeListener(mLayoutChangeListener);
//		}

		if (!mFromXml) {
			// FrameLayout.LayoutParams frameParams = new
			// FrameLayout.LayoutParams(
			// ViewGroup.LayoutParams.MATCH_PARENT,
			// ViewGroup.LayoutParams.MATCH_PARENT
			// );
			//
			// removeAllViews();
			// View v = makeControllerView();
			// addView(v, frameParams);
		}

	}
	
	private MediaControlListener mMediaControlListener;
	
	public interface MediaControlListener{
		public void onClickStart();
		public void onClickPause();
	}

	/**
	 * Create the view that holds the widgets that control playback. Derived
	 * classes can override this to create their own.
	 * 
	 * @return The controller view.
	 * @hide This doesn't work as advertised
	 */
	// protected View makeControllerView() {
	// LayoutInflater inflate = (LayoutInflater)
	// mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// mRoot = inflate.inflate(com.android.internal.R.layout.media_controller,
	// null);
	//
	// initControllerView(mRoot);
	//
	// return mRoot;
	// }

	private void initControllerView(View v) {
		mPauseButton = (ImageView) v.findViewById(R.id.mediacontroller_play_pause);
//		if (mPauseButton != null) {
//			mPauseButton.requestFocus();
//			mPauseButton.setOnClickListener(mPauseListener);
//		}

		if (mShareBtn != null) {
//			mShareBtn.setOnClickListener(mShareListener);
		}
		
		

//		mFfwdButton = (ImageButton) v.findViewById(com.android.internal.R.id.ffwd);
//		if (mFfwdButton != null) {
//			mFfwdButton.setOnClickListener(mFfwdListener);
//			if (!mFromXml) {
//				mFfwdButton.setVisibility(mUseFastForward ? View.VISIBLE
//						: View.GONE);
//			}
//		}
//
//		mRewButton = (ImageButton) v.findViewById(com.android.internal.R.id.rew);
//		if (mRewButton != null) {
//			mRewButton.setOnClickListener(mRewListener);
//			if (!mFromXml) {
//				mRewButton.setVisibility(mUseFastForward ? View.VISIBLE
//						: View.GONE);
//			}
//		}

		// By default these are hidden. They will be enabled when
		// setPrevNextListeners() is called
//		mNextButton = (ImageButton) v.findViewById(com.android.internal.R.id.next);
//		if (mNextButton != null && !mFromXml && !mListenersSet) {
//			mNextButton.setVisibility(View.GONE);
//		}
//		mPrevButton = (ImageButton) v.findViewById(com.android.internal.R.id.prev);
//		if (mPrevButton != null && !mFromXml && !mListenersSet) {
//			mPrevButton.setVisibility(View.GONE);
//		}

		mProgress = (ProgressBar) v.findViewById(R.id.mediacontroller_seekbar);
		if (mProgress != null) {
			if (mProgress instanceof SeekBar) {
				SeekBar seeker = (SeekBar) mProgress;
				seeker.setOnSeekBarChangeListener(mSeekListener);
			}
			mProgress.setMax(1000);
		}

		mEndTime = (TextView) v.findViewById(R.id.mediacontroller_time_total);
		mCurrentTime = (TextView) v.findViewById(R.id.mediacontroller_time_current);
		mFormatBuilder = new StringBuilder();
		mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

		installPrevNextListeners();
	}

	/**
	 * Show the controller on screen. It will go away automatically after 3
	 * seconds of inactivity.
	 */
	public void show() {
		show(sDefaultTimeout);
	}

	/**
	 * Disable pause or seek buttons if the stream cannot be paused or seeked.
	 * This requires the control interface to be a MediaPlayerControlExt
	 */
	private void disableUnsupportedButtons() {
		try {
			if (mPauseButton != null && !mPlayer.canPause()) {
				mPauseButton.setEnabled(false);
			}
			if (mRewButton != null && !mPlayer.canSeekBackward()) {
				mRewButton.setEnabled(false);
			}
			if (mFfwdButton != null && !mPlayer.canSeekForward()) {
				mFfwdButton.setEnabled(false);
			}
		} catch (IncompatibleClassChangeError ex) {
			// We were given an old version of the interface, that doesn't have
			// the canPause/canSeekXYZ methods. This is OK, it just means we
			// assume the media can be paused and seeked, and so we don't
			// disable
			// the buttons.
		}
	}

	/**
	 * Show the controller on screen. It will go away automatically after
	 * 'timeout' milliseconds of inactivity.
	 * 
	 * @param timeout
	 *            The timeout in milliseconds. Use 0 to show the controller
	 *            until hide() is called.
	 */
	public void show(int timeout) {
		if (!mShowing && mAnchor != null) {
			
			if (mFromXml) {
				setVisibility(View.VISIBLE);
//				return;
			}
			
			setProgress();
			if (mPauseButton != null) {
				mPauseButton.requestFocus();
			}
			disableUnsupportedButtons();
//			updateFloatingWindowLayout();
//			mWindowManager.addView(mDecor, mDecorLayoutParams);
			mShowing = true;
		}
		updatePausePlay();

		// cause the progress bar to be updated even if mShowing
		// was already true. This happens, for example, if we're
		// paused with the progress bar showing the user hits play.
		mHandler.sendEmptyMessage(SHOW_PROGRESS);
		
	   // DebugTools.d("control2 show");

		Message msg = mHandler.obtainMessage(FADE_OUT);
		if (timeout != 0) {
			mHandler.removeMessages(FADE_OUT);
			mHandler.sendMessageDelayed(msg, timeout);
		}
	}

	public boolean isShowing() {
		return mShowing;
	}

	/**
	 * Remove the controller from the screen.
	 */
	public void hide() {
//		if (mAnchor == null)
//			return;
//
//		if (mShowing) {
//			try {
//				mHandler.removeMessages(SHOW_PROGRESS);
//				mWindowManager.removeView(mDecor);
//			} catch (IllegalArgumentException ex) {
//				Log.w("MediaController", "already removed");
//			}
//			mShowing = false;
//		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int pos;
			switch (msg.what) {
			case FADE_OUT:
//				hide();
				break;
			case SHOW_PROGRESS:
				pos = setProgress();
				if (!mDragging && mShowing && mPlayer.isPlaying()) {
					msg = obtainMessage(SHOW_PROGRESS);
					sendMessageDelayed(msg, 1000 - (pos % 1000));
				}
				break;
			}
		}
	};

	private String stringForTime(int timeMs) {
		int totalSeconds = timeMs / 1000;

		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;

		mFormatBuilder.setLength(0);
		if (hours > 0) {
			return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
					.toString();
		} else {
			return mFormatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}

	private int setProgress() {
		//DebugTools.d("control2 setProgress");
		
		if (mPlayer == null || mDragging) {
			return 0;
		}
		int position = mPlayer.getCurrentPosition();
		int duration = mPlayer.getDuration();
		
		//DebugTools.d("control2 setProgress position: " + position + " duration: " + duration);
		
		if (mProgress != null) {
			if (duration > 0) {
				// use long to avoid overflow
				long pos = 1000L * position / duration;
				mProgress.setProgress((int) pos);
			}
			int percent = mPlayer.getBufferPercentage();
			mProgress.setSecondaryProgress(percent * 10);
		}

		if (mEndTime != null)
			mEndTime.setText(stringForTime(duration));
		if (mCurrentTime != null)
			mCurrentTime.setText(stringForTime(position));

		return position;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		show(sDefaultTimeout);
		return true;
	}

	@Override
	public boolean onTrackballEvent(MotionEvent ev) {
		show(sDefaultTimeout);
		return false;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		int keyCode = event.getKeyCode();
		final boolean uniqueDown = event.getRepeatCount() == 0
				&& event.getAction() == KeyEvent.ACTION_DOWN;
		if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
				|| keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
				|| keyCode == KeyEvent.KEYCODE_SPACE) {
			if (uniqueDown) {
				doPauseResume();
				show(sDefaultTimeout);
				if (mPauseButton != null) {
					mPauseButton.requestFocus();
				}
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
			if (uniqueDown && !mPlayer.isPlaying()) {
				mPlayer.start();
				updatePausePlay();
				show(sDefaultTimeout);
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
				|| keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
			if (uniqueDown && mPlayer.isPlaying()) {
				mPlayer.pause();
				updatePausePlay();
				show(sDefaultTimeout);
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
				|| keyCode == KeyEvent.KEYCODE_VOLUME_UP
				|| keyCode == KeyEvent.KEYCODE_VOLUME_MUTE
				|| keyCode == KeyEvent.KEYCODE_CAMERA) {
			// don't show the controls for volume adjustment
			return super.dispatchKeyEvent(event);
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_MENU) {
			if (uniqueDown) {
				hide();
			}
			return true;
		}

		show(sDefaultTimeout);
		return super.dispatchKeyEvent(event);
	}

	private OnClickListener mPauseListener = new OnClickListener() {
		public void onClick(View v) {
			doPauseResume();
			show(sDefaultTimeout);
		}
	};
	
	private OnClickListener mShareListener = new OnClickListener() {
		public void onClick(View v) {
			if(mMediaControlListener != null){
//				mMediaControlListener.onClickShare(v);
			}
		}
	};
	
	
	
	public void setMediaControlListener(MediaControlListener listener){
		mMediaControlListener = listener;
	}

	private void updatePausePlay() {
		if (mRoot == null || mPauseButton == null)
			return;

//		if (mPlayer.isPlaying()) {
//			mPauseButton.setImageResource(R.drawable.mediacontroller_pause);
////			mPauseButton.setImageResource(com.android.internal.R.drawable.ic_media_pause);
//		} else {
//			mPauseButton.setImageResource(R.drawable.mediacontroller_play);
////			mPauseButton.setImageResource(com.android.internal.R.drawable.ic_media_play);
//		}
	}

	public void doPause(){
		if (mPlayer.isPlaying()) {
			mPlayer.pause();
			mPauseButton.setImageResource(R.mipmap.library_video_mediacontroller_play);
			if(mMediaControlListener != null){
				mMediaControlListener.onClickPause();
			}
		}
	}
	
	public void doPauseResume() {
		if (mPlayer.isPlaying()) {
			mPlayer.pause();
			mPauseButton.setImageResource(R.mipmap.library_video_mediacontroller_play);
			if(mMediaControlListener != null){
				mMediaControlListener.onClickPause();
			}
		} else {
			mPlayer.start();
			mPauseButton.setImageResource(R.mipmap.library_video_mediacontroller_pause);
			if(mMediaControlListener != null){
				mMediaControlListener.onClickStart();
			}
		}
//		updatePausePlay();
	}

	// There are two scenarios that can trigger the seekbar listener to trigger:
	//
	// The first is the user using the touchpad to adjust the posititon of the
	// seekbar's thumb. In this case onStartTrackingTouch is called followed by
	// a number of onProgressChanged notifications, concluded by
	// onStopTrackingTouch.
	// We're setting the field "mDragging" to true for the duration of the
	// dragging
	// session to avoid jumps in the position in case of ongoing playback.
	//
	// The second scenario involves the user operating the scroll ball, in this
	// case there WON'T BE onStartTrackingTouch/onStopTrackingTouch
	// notifications,
	// we will simply apply the updated position without suspending regular
	// updates.
	private OnSeekBarChangeListener mSeekListener = new OnSeekBarChangeListener() {
		public void onStartTrackingTouch(SeekBar bar) {
			show(3600000);

			mDragging = true;

			// By removing these pending progress messages we make sure
			// that a) we won't update the progress while the user adjusts
			// the seekbar and b) once the user is done dragging the thumb
			// we will post one of these messages to the queue again and
			// this ensures that there will be exactly one message queued up.
			mHandler.removeMessages(SHOW_PROGRESS);
			
			if(mAdditionalSeekListener != null){
				mAdditionalSeekListener.onStartTrackingTouch(bar);
			}
		}

		public void onProgressChanged(SeekBar bar, int progress,
                                      boolean fromuser) {
			if (!fromuser) {
				// We're not interested in programmatically generated changes to
				// the progress bar's position.
				return;
			}

			long duration = mPlayer.getDuration();
			long newposition = (duration * progress) / 1000L;
			mPlayer.seekTo((int) newposition);
			if (mCurrentTime != null)
				mCurrentTime.setText(stringForTime((int) newposition));
			
			if(mAdditionalSeekListener != null){
				mAdditionalSeekListener.onProgressChanged(bar, progress, fromuser);
			}
		}

		public void onStopTrackingTouch(SeekBar bar) {
			mDragging = false;
			setProgress();
			updatePausePlay();
			show(sDefaultTimeout);

			// Ensure that progress is properly updated in the future,
			// the call to show() does not guarantee this because it is a
			// no-op if we are already showing.
			mHandler.sendEmptyMessage(SHOW_PROGRESS);
			
			if(mAdditionalSeekListener != null){
				mAdditionalSeekListener.onStopTrackingTouch(bar);
			}
		}
	};
	
	private OnSeekBarChangeListener mAdditionalSeekListener;
	public void setAdditionalSeekBarListener(OnSeekBarChangeListener listener){
		mAdditionalSeekListener = listener;
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (mPauseButton != null) {
			mPauseButton.setEnabled(enabled);
		}
		if (mFfwdButton != null) {
			mFfwdButton.setEnabled(enabled);
		}
		if (mRewButton != null) {
			mRewButton.setEnabled(enabled);
		}
		if (mNextButton != null) {
			mNextButton.setEnabled(enabled && mNextListener != null);
		}
		if (mPrevButton != null) {
			mPrevButton.setEnabled(enabled && mPrevListener != null);
		}
		if (mProgress != null) {
			mProgress.setEnabled(enabled);
		}
		disableUnsupportedButtons();
		super.setEnabled(enabled);
	}

	@Override
	public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
		super.onInitializeAccessibilityEvent(event);
		event.setClassName(MediaController.class.getName());
	}

	@Override
	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
		super.onInitializeAccessibilityNodeInfo(info);
		info.setClassName(MediaController.class.getName());
	}

	private OnClickListener mRewListener = new OnClickListener() {
		public void onClick(View v) {
			int pos = mPlayer.getCurrentPosition();
			pos -= 5000; // milliseconds
			mPlayer.seekTo(pos);
			setProgress();

			show(sDefaultTimeout);
		}
	};

	private OnClickListener mFfwdListener = new OnClickListener() {
		public void onClick(View v) {
			int pos = mPlayer.getCurrentPosition();
			pos += 15000; // milliseconds
			mPlayer.seekTo(pos);
			setProgress();

			show(sDefaultTimeout);
		}
	};
	
	public void seekTo(int pos){
		mPlayer.seekTo(pos);
		setProgress();

		show(sDefaultTimeout);
	}

	private void installPrevNextListeners() {
		if (mNextButton != null) {
			mNextButton.setOnClickListener(mNextListener);
			mNextButton.setEnabled(mNextListener != null);
		}

		if (mPrevButton != null) {
			mPrevButton.setOnClickListener(mPrevListener);
			mPrevButton.setEnabled(mPrevListener != null);
		}
	}

	public void setPrevNextListeners(OnClickListener next,
			OnClickListener prev) {
		mNextListener = next;
		mPrevListener = prev;
		mListenersSet = true;

		if (mRoot != null) {
			installPrevNextListeners();

			if (mNextButton != null && !mFromXml) {
				mNextButton.setVisibility(View.VISIBLE);
			}
			if (mPrevButton != null && !mFromXml) {
				mPrevButton.setVisibility(View.VISIBLE);
			}
		}
	}
}
