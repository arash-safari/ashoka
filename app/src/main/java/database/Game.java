package database;

import android.support.annotation.NonNull;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Game {
    @Id public long id;
    @NonNull public long server_id;
    @NonNull public String link;
    @NonNull public String logoLink;
    @NonNull public String imageSrc;
    @NonNull public String name;
    @NonNull public boolean active;
}
