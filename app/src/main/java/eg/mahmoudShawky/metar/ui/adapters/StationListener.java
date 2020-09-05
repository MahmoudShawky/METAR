package eg.mahmoudShawky.metar.ui.adapters;

import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;

/***
 * @author mahmoud.shawky
 *
 * Listener interface to be used as a callback on {@link StationsAdapter} list item actions
 */
public interface StationListener {

    void onItemClicked(StationEntity station);

    void onAddToFavClicked(StationEntity station, boolean isAdd);

}
