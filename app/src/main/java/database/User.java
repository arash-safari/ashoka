package database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class User {
    @Id
    public long id;
    @Nullable public String name;
    @Nullable public String family;
    @Nullable public long server_id;
    @Nullable public String paye;
    @Nullable public String code_daneshamoozi;
    @Nullable public String jensiat;
}
