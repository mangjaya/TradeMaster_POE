package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import org.jsoup.nodes.Element;

import gui.MainFrame;
import handler.JsonNinjaSearchData;
import handler.PoeNinjaHandler;
import handler.PoeTradeHandler;
import items.Map;
import items.Maps;
import items.TradeableBulk;

	public class UpdateButtonBulksListener implements ActionListener {

		private MainFrame frame;
		
		public UpdateButtonBulksListener(MainFrame frame) {
			this.frame = frame;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getLbl_tradeables_bulks().setText("Loading...");
			frame.getBtn_update_bulks().setEnabled(false);
			int bulkAmount = Integer.valueOf( frame.getTxt_amount_bulks().getText() );
			boolean isElder = frame.getChckbxElderMap().isSelected();
			String mapFromCmbBox = frame.getCmb_maps_bulks().getSelectedItem().toString();
			PoeNinjaHandler ninjaHandler = new PoeNinjaHandler(bulkAmount);
			JsonNinjaSearchData searchData = new JsonNinjaSearchData();
			
			// TODO: Generate json searchData...
			String map = "";
			if(isElder) {
				map += "elder-";
			}
			map += mapFromCmbBox.replaceAll(" ", "-");
			map += "-map"; 
			
			searchData.setBulkAmount(bulkAmount);
			searchData.setMap(map);
			ninjaHandler.setSearchData(searchData);
			ninjaHandler.generateJsonSearchString();
			ninjaHandler.handleBulkRequests();
			
			frame.setTradeables( ninjaHandler.getTradeableBulks() );
			
			// Filter by currency and price per map
			if(frame.getTxtbox_pricePerMap().isEnabled() && frame.isValidPricePerMapInput()) {
				int pricePerMap;
				String currency;
				
				pricePerMap = Integer.valueOf(frame.getTxtbox_pricePerMap().getText());
				currency = (String )frame.getCmb_currency_bulks().getSelectedItem();
				
				frame.getTradeables().filterByCurrencyAndMaxPrice(currency, pricePerMap);
			}
			
			frame.getLbl_tradeables_bulks().setText("Tradeables: " + frame.getTradeables().getFilteredTradeableItems().size());
			
			
			if(frame.getTradeables().getFilteredTradeableItems().size() > 0 ) {
				frame.getBtn_nextTrade_bulks().setEnabled(true);
			}
		}

	}
