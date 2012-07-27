Ext.define('Ssp.service.PersonService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	person: 'currentPerson',
    	sspConfig: 'sspConfig'
    },
    config: {
    	personUrl: null
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') );
    	return baseUrl;
    },

    get: function( id, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	if (response.responseText != "")
	    	{
		    	r = Ext.decode(response.responseText);	    		
	    	}
	    	callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		// load the person to edit
		me.apiProperties.makeRequest({
			url: me.getBaseUrl()+'/'+id,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },   

    getBySchoolId: function( schoolId, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r;
	    	if (response != null)
	    	{
		    	if (response.responseText != "")
		    	{
		    		r = Ext.decode(response.responseText);
		    	}		    		
	    	}
	    	callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		// load the person to edit
		me.apiProperties.makeRequest({
			url: me.getBaseUrl()+'/bySchoolId/'+schoolId,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },    
    
    save: function( jsonData, callbacks ){
    	var me=this;
    	var id=jsonData.id;
        var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	var r;
	    	// handle unique schoolId error display in a more
	    	// user friendly fashion
	    	/*
	    	if ( response.responseText != null)
	    	{
	    		r = Ext.decode(response.responseText);
	    		if (r.message.indexOf('ERROR: duplicate key value violates unique constraint \"uq_person_school_id\"') == 0)
	    		{
	    			Ext.Msg.alert("SSP Error","The " + me.sspConfig.get('studentIdAlias') + " you entered already exists in the system. Please double-check and try again.");
	    		}
	    	}else{
		    	    		
	    	}
	    	*/
	    	me.apiProperties.handleError( response );
	    	callbacks.failure( response, callbacks.scope );	
	    };
        
    	// save the person
		if (id=="")
		{
			// create
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}   	
    },
    
    destroy: function( id, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
    	me.apiProperties.makeRequest({
   		   url: me.getBaseUrl()+"/"+id,
   		   method: 'DELETE',
   		   successFunc: success,
   		   failureFunc: failure,
   		   scope: me
   	    }); 
    }
});