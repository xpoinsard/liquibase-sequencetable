/**
 * 
 */
package liquibase.ext.sequencetable.precondition;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.precondition.core.SequenceExistsPrecondition;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;

/**
 * @author xpoinsar
 *
 */
public class SequenceTableExistsPrecondition extends	SequenceExistsPrecondition {

	/* (non-Javadoc)
	 * @see liquibase.precondition.core.SequenceExistsPrecondition#check(liquibase.database.Database, liquibase.changelog.DatabaseChangeLog, liquibase.changelog.ChangeSet)
	 */
	@Override
	public void check(Database database, DatabaseChangeLog changeLog,
			ChangeSet changeSet) throws PreconditionFailedException,
			PreconditionErrorException {
	
	     Schema schema = new Schema(getCatalogName(), getSchemaName());
	     if (!database.supportsSequences()){
	      	try {
		        if (!SnapshotGeneratorFactory.getInstance().has(new Table().setName(getSequenceName()).setSchema(schema), database)) {
		                throw new PreconditionFailedException("Table "+database.escapeSequenceName(getCatalogName(), getSchemaName(), getSequenceName())+" does not exist", changeLog, this);
	            }
	        } catch (LiquibaseException e) {
	            throw new PreconditionErrorException(e, changeLog, this);
	        }	
        }else{
        	super.check(database, changeLog, changeSet);
        }
        	
	}
	
	

}
