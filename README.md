# prog2_ybel_zoo

## Blatt 08 Reflexion

### Optional

**Warum ist Optional hier sinnvoll?**

- Optional macht deutlich, dass eine Suche kein Ergebnis liefern muss.
- Dadurch werden `null`-Rückgaben vermieden.
- Der Aufrufer muss den Fall eines fehlenden Ergebnisses bewusst behandeln.

**Warum Optional<T> in Enclosure und Optional<Animal> in Zoo?**

- `Enclosure<T>` kennt den konkreten Tier-Typ und kann daher `Optional<T>` zurückgeben.
- Der Zoo enthält verschiedene Gehegetypen, deshalb wird `Optional<Animal>` verwendet.

---

### Command Pattern

**Welche Vorteile bietet das Command Pattern?**

- Aktionen werden als eigene Objekte modelliert.
- Undo und Redo lassen sich einfach umsetzen.
- Neue Befehle können leicht ergänzt werden.

---

### Result<E,R>

**Warum Result statt Exceptions?**

- Mögliche Fehler sind bereits im Rückgabetyp sichtbar.
- Fehler müssen vom Aufrufer behandelt werden.
- Es wird kein Exception-Handling benötigt.

**Welche Rolle spielt der CommandManager?**

- Er führt Commands aus.
- Er verwaltet Undo und Redo.
- Er übernimmt das Logging zentral.