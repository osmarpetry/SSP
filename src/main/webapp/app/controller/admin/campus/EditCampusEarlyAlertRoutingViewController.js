Ext.define('Ssp.controller.admin.campus.EditCampusEarlyAlertRoutingViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampusEarlyAlertRouting'
    },
    config: {
    	containerToLoadInto: 'campusearlyalertroutingsadmin',
    	formToDisplay: 'earlyalertroutingsadmin'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
    
	init: function() {
		var me=this;
		me.getView().getForm().reset();
		me.getView().getForm().loadRecord( me.model );
		return me.callParent(arguments);
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		url = this.store.getProxy().url;
		this.getView().getForm().updateRecord();
		record = this.model;
		id = record.get('id');
		jsonData = record.data;
		successFunc = function(response, view) {
			me.displayMain();
		};
		
		if (id.length > 0)
		{
			// editing
			this.apiProperties.makeRequest({
				url: url+"/"+id,
				method: 'PUT',
				jsonData: jsonData,
				successFunc: successFunc 
			});
			
		}else{
			// adding
			this.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: jsonData,
				successFunc: successFunc 
			});		
		}
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});