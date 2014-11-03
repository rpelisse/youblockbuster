package org.belaran.redhat.youblockbuster;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.infinispan.distexec.DefaultExecutorService;
import org.infinispan.distexec.DistributedExecutorService;
import org.infinispan.distexec.DistributedTaskBuilder;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.remoting.transport.Address;



@Indexed
public class Actor implements Serializable {
	private static final long serialVersionUID = 5988630561394211285L;

	@Field(store = Store.YES)
	private String name;

	@Field
	private int age;
	
	//    ...

	@SuppressWarnings("null")
	public void m() {
		EmbeddedCacheManager manager = null;//InfinispanUtils.buildInfinispanConfiguration();
		
		DistributedExecutorService des = new DefaultExecutorService(manager.getCache());

		des.submitEverywhere( new RegularCallable("RAN ON EVERY NODE !!!")	);
		
		List<Address> n = manager.getMembers();
		
		int nbTaskToRun = 0;
		
		while (nbTaskToRun > 0 )
			for ( Address node : manager.getMembers() )
				System.out.println(des.submit(node, 
					new RegularCallable("Task[" + nbTaskToRun-- + 
							"] ran on node with address: " + node.toString())));

DistributedTaskBuilder<String> taskBuilder =
				des.createDistributedTaskBuilder(new RegularCallable("..."));
taskBuilder.failoverPolicy(DefaultExecutorService.RANDOM_NODE_FAILOVER);
		
	}
	
	class RegularCallable implements Callable<String> {

		public RegularCallable(String s){};

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
/*
 * 
    public static List<Object> searchValueByIndexedField(Cache<Object,Object> c, String indexedFieldName, String indexedValue) {
        SearchManager searchManager = Search.getSearchManager(c);
        QueryBuilder builder = searchManager.buildQueryBuilderForClass(Actor.class).get();
        Query query = builder.bool()
                .must(builder.keyword().onField(indexedFieldName)
                .matching(indexedValue).createQuery())
                .createQuery();
        return searchManager.getQuery(query, Actor.class).list();
    }
*/
