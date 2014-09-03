package org.openzal.zal.lib;

import org.openzal.zal.ZEMailbox;
import com.zimbra.cs.db.DbMailItem;
import com.zimbra.cs.db.DbMailbox;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class ZimbraDatabaseTest
{
  @Test
  public void qualify_zimbra_table_name_doesnt_change()
  {
    String result = DbMailbox.qualifyZimbraTableName(32, "mailbox_metadata");
    assertEquals("mailbox_metadata",result);
  }

  @Test
  public void mail_item_table_name_doesnt_change()
  {
    final String zimbraTableName;
/*$if ZimbraVersion >= 8.0.2 $*/
    zimbraTableName = DbMailItem.getMailItemTableName(
      32,
      false
    );
/*$endif $*/

/*$if ZimbraVersion >= 7.0.0 && ZimbraVersion < 8.0.2 $
    zimbraTableName = DbMailItem.getMailItemTableName(
      32,
      32,
      false
    );
/*$endif $*/

/*$if ZimbraVersion < 7.0.0 $
    zimbraTableName = DbMailItem.getMailItemTableName(
      32,
      32
    );
/*$endif $*/

    ZEMailbox mbox = Mockito.mock(ZEMailbox.class);
    when( mbox.getSchemaGroupId() ).thenReturn(32);
    String zextrasTableName = ZimbraDatabase.getItemTableName(mbox);

    assertEquals(zimbraTableName,zextrasTableName);
  }

  @Test
  public void tombstone_table_name_doesnt_change()
  {
    final String zimbraTableName;

    zimbraTableName = DbMailItem.getTombstoneTableName(
      32,
      32
    );


    ZEMailbox mbox = Mockito.mock(ZEMailbox.class);
    when( mbox.getSchemaGroupId() ).thenReturn(32);
    String zextrasTableName = ZimbraDatabase.getTombstoneTable(mbox);

    assertEquals(zimbraTableName,zextrasTableName);
  }

  @Test
  public void revision_table_name_doesnt_change()
  {
    final String zimbraTableName;

/*$if ZimbraVersion >= 8.0.2 $*/
    zimbraTableName = DbMailItem.getRevisionTableName(
      32,
      false
    );
/*$endif $*/

/*$if ZimbraVersion >= 7.0.0 && ZimbraVersion < 8.0.2 $
    zimbraTableName = DbMailItem.getRevisionTableName(
      32,
      32,
      false
    );
/*$endif $*/

/*$if ZimbraVersion < 7.0.0 $
    zimbraTableName = DbMailItem.getRevisionTableName(
      32,
      32
    );
/*$endif $*/

    ZEMailbox mbox = Mockito.mock(ZEMailbox.class);
    when( mbox.getSchemaGroupId() ).thenReturn(32);
    String zextrasTableName = ZimbraDatabase.getRevisionTableName(mbox);

    assertEquals(zimbraTableName,zextrasTableName);
  }

}
