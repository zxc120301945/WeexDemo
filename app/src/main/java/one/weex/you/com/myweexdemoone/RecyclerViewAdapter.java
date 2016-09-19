package one.weex.you.com.myweexdemoone;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView Adapter的抽象类
 * 
 * @author WanZhiYuan
 * 
 * @param <T>
 */
public abstract class RecyclerViewAdapter<T extends RecyclerViewAdapter.Item>
		extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	protected List<T> list = null;
	protected int headerViewRes;
	protected int footerViewRes;
	protected boolean hasHeader = false;
	protected boolean hasFooter = false;
	protected int mFooterLoadingResId, mFooterNoMoreResId,
			mFooterNoNetwordResId;
	// /**
	// * Footer布局模式：加载中，没有更多，没有网络（前提是必须setFooterView、setFooterViewLoadingResId�?
	// * setFooterViewNoMoewResId、setFooterViewNoNetworkResId且此三个布局在一个文件里
	// **/
	// public enum FooterMode {
	// MODE_LOADING, MODE_NO_NORE, MODE_NO_NETWORK
	// };
	//
	// private FooterMode mFooterMode = FooterMode.MODE_LOADING;
	// protected IItemClickFooterNoNetwork mFooterNoNetworkClickListener;
	//
	// /**
	// * 设置Footer布局模式
	// *
	// * @param mode
	// * @see FooterMode
	// */
	// public void setFooterMode(FooterMode mode) {
	// this.mFooterMode = mode;
	// }
	//
	// public FooterMode getFooterMode(){
	// if(this.hasFooter()){
	// return mFooterMode;
	// }
	// return null;
	// }
	//
	// public void setItemClickFooterNoNetwork(
	// IItemClickFooterNoNetwork mFooterNoNetworkClickListener) {
	// this.mFooterNoNetworkClickListener = mFooterNoNetworkClickListener;
	// }

	public RecyclerViewAdapter(List<T> list) {
		this.list = list;
	}

	public RecyclerViewAdapter(List<T> list, int headerViewRes) {
		this.list = list;
		setHeaderView(headerViewRes);
	}

	public RecyclerViewAdapter(List<T> list, int headerViewRes,
			int footerViewRes) {
		this.list = list;
		setHeaderView(headerViewRes);
		setFooterView(footerViewRes);
		// this.addFooter(footerViewRes);
	}
	
	public interface Item {
		int TYPE_HEADER = -1;
		int TYPE_FOOTER = -2;

		/**
		 * 返回item类型，其值不能为-1或-2
		 * 
		 * @return
		 */
		int getItemType();
	}

	@Override
	public int getItemCount() {
		int count = 0;
		count += (hasHeader() ? 1 : 0);
		count += (hasFooter() ? 1 : 0);
		count += list.size();
		return count;
	}

	@Override
	public int getItemViewType(int position) {
		int size = list.size();
		if (hasHeader()) {
			if (position == 0) {
				return Item.TYPE_HEADER;
			} else {
				if (position == size + 1) {
					return Item.TYPE_FOOTER;
				} else {
					return list.get(position - 1).getItemType();
				}
			}

		} else {
			if (position == size) {
				return Item.TYPE_FOOTER;
			} else {
				return list.get(position).getItemType();
			}
		}
	}
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public boolean isHeader(int position) {
		return hasHeader() && position == 0;
	}

	public boolean isFooter(int position) {
		if (hasHeader()) {
			return hasFooter() && position == list.size() + 1;
		} else {
			return hasFooter() && position == list.size();
		}
	}

	public int getHeaderView() {
		return headerViewRes;
	}

	public int getFooterView() {
		return footerViewRes;
	}

	public void setHeaderView(int headerViewRes) {

		if (headerViewRes != 0) {
			if (!hasHeader()) {
				this.headerViewRes = headerViewRes;
				this.hasHeader = true;
				notifyItemInserted(0);
			} else {
				this.headerViewRes = headerViewRes;
				notifyDataSetChanged();
			}

		} else {
			if (hasHeader()) {
				this.hasHeader = false;
				notifyItemRemoved(0);
			}

		}

	}

	public void setFooterView(int footerViewRes) {
		// if (footerViewRes != 0) {
		if (footerViewRes > 0) {
			if (!hasFooter()) {
				this.footerViewRes = footerViewRes;
				this.hasFooter = true;
				if (hasHeader()) {
					notifyItemInserted(list.size() + 1);
				} else {
					notifyItemInserted(list.size());
				}
			} else {
				this.footerViewRes = footerViewRes;
				// notifyDataSetChanged();
				if (hasHeader()) {
					notifyItemChanged(list.size() + 1);
				} else {
					notifyItemChanged(list.size());
				}
			}

		} else {
			if (hasFooter()) {
				this.hasFooter = false;
				if (hasHeader()) {
					notifyItemRemoved(list.size() + 1);
				} else {
					notifyItemRemoved(list.size());
				}

			}

		}

	}

	// public void addFooter(int footerViewRes) {
	// if (footerViewRes <= 0 || this.hasFooter()) {
	// return;
	// }
	// this.footerViewRes = footerViewRes;
	// this.hasFooter = true;
	// if (hasHeader()) {
	// this.notifyItemInserted(list.size() + 1);
	// } else {
	// this.notifyItemInserted(list.size());
	// }
	// }
	//
	// public void removeFooter() {
	// if (!hasFooter()) {
	// return;
	// }
	// this.footerViewRes = GlobalConfig.INVALID;
	// this.hasFooter = false;
	// if (hasHeader()) {
	// notifyItemRemoved(list.size() + 1);
	// } else {
	// notifyItemRemoved(list.size());
	// }
	//
	// }
	//
	// public void setFooterViewLoadingResId(int loadingResId) {
	// this.mFooterLoadingResId = loadingResId;
	// }
	//
	// public void setFooterViewNoMoreResId(int noMoreResId) {
	// this.mFooterNoMoreResId = noMoreResId;
	// }
	//
	// public void setFooterViewNoNetworkResId(int noNetworkResId) {
	// this.mFooterNoNetwordResId = noNetworkResId;
	// }

	public boolean hasHeader() {
		return hasHeader;
	}

	public boolean hasFooter() {
		return hasFooter;
	}

	static class HeaderViewHolder extends RecyclerView.ViewHolder {
		public HeaderViewHolder(View itemView) {
			super(itemView);
		}
	}

	static class FooterViewHolder extends RecyclerView.ViewHolder {
		public FooterViewHolder(View itemView) {
			super(itemView);
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
													  int viewType) {
		if (hasHeader() && viewType == Item.TYPE_HEADER) {
			View v = LayoutInflater.from(parent.getContext()).inflate(
					getHeaderView(), parent, false);
			return new HeaderViewHolder(v);
		} else if (hasFooter() && viewType == Item.TYPE_FOOTER) {
			View v = LayoutInflater.from(parent.getContext()).inflate(
					getFooterView(), parent, false);
			return new FooterViewHolder(v);
		} else {
			return onCreateHolder(parent, viewType);
		}
	}

	public abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent,
														   int viewType);

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (getItemViewType(position) == Item.TYPE_HEADER) {
			HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
			View headerView = headerHolder.itemView;

			onBindHeaderView(headerView);
		} else if (getItemViewType(position) == Item.TYPE_FOOTER) {
			FooterViewHolder footerHolder = (FooterViewHolder) holder;
			View footerView = footerHolder.itemView;

			onBindFooterView(footerView);
		} else {
			T i = getItemByPosition(position);
			onBindItemView(holder, i, position);

		}

	}

	protected T getItemByPosition(int position) {
		int size = list.size();
		if (hasHeader()) {
			return list.get(position - 1);
		} else {
			return list.get(position);
		}
	}

	protected abstract void onBindHeaderView(View headerView);

	protected void onBindFooterView(View footerView) {
		// View loadingView = footerView.findViewById(mFooterLoadingResId);
		// View noMoreView = footerView.findViewById(mFooterNoMoreResId);
		// View noNetworkView = footerView.findViewById(mFooterNoNetwordResId);
		// if (FooterMode.MODE_LOADING == mFooterMode) {
		// this.setViewVisibility(loadingView, View.VISIBLE);
		// this.setViewVisibility(noMoreView, View.GONE);
		// this.setViewVisibility(noNetworkView, View.GONE);
		// } else if (FooterMode.MODE_NO_NORE == mFooterMode) {
		// this.setViewVisibility(loadingView, View.GONE);
		// this.setViewVisibility(noMoreView, View.VISIBLE);
		// this.setViewVisibility(noNetworkView, View.GONE);
		// } else if (FooterMode.MODE_NO_NETWORK == mFooterMode) {
		// this.setViewVisibility(loadingView, View.GONE);
		// this.setViewVisibility(noMoreView, View.GONE);
		// this.setViewVisibility(noNetworkView, View.VISIBLE);
		// footerView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (null != mFooterNoNetworkClickListener) {
		// mFooterNoNetworkClickListener
		// .onItemClickFooterNoNetwork(v);
		// }
		// }
		// });
		// }
	};

	protected abstract void onBindItemView(RecyclerView.ViewHolder holder,
			T item, int position);

	// private void setViewVisibility(View view, int visibility) {
	// if (null != view) {
	// view.setVisibility(visibility);
	// }
	// }
	//
	// /**
	// * Footer布局里没网时点击事件
	// *
	// * @author Administrator
	// *
	// */
	// public interface IItemClickFooterNoNetwork {
	// /**
	// * 加载更多失败时，点击可加�?
	// * @param view
	// */
	// public void onItemClickFooterNoNetwork(View view);
	// }
	
}
