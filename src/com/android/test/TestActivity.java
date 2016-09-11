package com.android.test;

import com.andrioid.firstlettericon.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TestActivity extends Activity {

	private DataSource mDataSource;
	private ListView mListview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		mDataSource = new DataSource(this);
		mListview = (ListView) findViewById(R.id.lv_list);
		mListview.setAdapter(new TextDrawableAdapter());
		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				DataItem item = (DataItem) mListview.getItemAtPosition(position);
		        if (item.getNavigationInfo() != DataSource.NO_NAVIGATION) {
		            Intent intent = new Intent(TestActivity.this, Next.class);
		            intent.putExtra("TYPE", item.getNavigationInfo());
		            startActivity(intent);
		        }
			}
		});
	}
	
	private class TextDrawableAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDataSource.getCount();
		}

		@Override
		public DataItem getItem(int position) {
			// TODO Auto-generated method stub
			return mDataSource.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if(convertView == null){
				convertView = View.inflate(TestActivity.this,
						R.layout.item_textdrawable, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			DataItem item = getItem(position);
			final Drawable drawable = item.getDrawable();
            holder.imageView.setImageDrawable(drawable);
            holder.textView.setText(item.getLabel());
            if (item.getNavigationInfo() != DataSource.NO_NAVIGATION) {
                holder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.ic_action_next_item), null);
            } else {
                holder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
                        null,  null, null);
            }
            // fix for animation not playing for some below 4.4 devices
            if (drawable instanceof AnimationDrawable) {
                holder.imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        ((AnimationDrawable) drawable).stop();
                        ((AnimationDrawable) drawable).start();
                    }
                });
            }
            return convertView;
		}

	    private  class ViewHolder {
	        private ImageView imageView;
	        private TextView textView;
	        private ViewHolder(View view) {
	            imageView = (ImageView) view.findViewById(R.id.iv_image);
	            textView = (TextView) view.findViewById(R.id.tv_text);
	        }
	    }
	}

}
