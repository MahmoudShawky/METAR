package eg.mahmoudShawky.metar.ui.adapters;

import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;

public interface StationListener {

    void onItemClicked(StationEntity station);

    void onAddToFavClicked(StationEntity station, boolean isAdd);

}
