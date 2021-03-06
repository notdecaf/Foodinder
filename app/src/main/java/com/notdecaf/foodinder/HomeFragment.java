package com.notdecaf.foodinder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	CardContainer mCardContainer;

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment HomeFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static HomeFragment newInstance(String param1, String param2) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public HomeFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_home, container, false);
		mCardContainer = (CardContainer) v.findViewById(R.id.layoutview);
		initCards();
		return v;
	}

	public void initCards() {
		mCardContainer.setOrientation(Orientations.Orientation.Disordered);
		CardModel card = new CardModel("Item Name","Address", getResources().getDrawable(R.drawable.burger));
		card.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
			@Override
			public void onLike() {
				Toast.makeText(getActivity(),"Liked",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onDislike() {
				Toast.makeText(getActivity(),"Liked",Toast.LENGTH_SHORT).show();
			}
		});
		SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(getActivity());
		adapter.add(card);
		mCardContainer.setAdapter(adapter);
	}
}
