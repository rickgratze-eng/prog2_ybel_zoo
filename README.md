# prog2_ybel_zoo

## Blatt 07 – Reflexion

### Generics

**Wo helfen Ihnen die Generics im Zoo-Szenario, Fehler bereits zur Compile-Zeit zu vermeiden?**

- Generics verhindern falsche Typkombinationen bereits zur Compile-Zeit.
- Dadurch können Gehege nur passende Tierarten aufnehmen.

**Beispiel**

- Ein `Aquarium<Trout>` kann keine `Dog`-Objekte aufnehmen.
- Ein `CatHouse<Lion>` kann keine `Dog`-Objekte aufnehmen.

---

### Logging

**Warum ist Logging sinnvoller als println?**

- Unterschiedliche Log-Level können verwendet werden.
- Programmabläufe lassen sich besser nachvollziehen.
- Logging kann gezielt aktiviert oder deaktiviert werden.

**Wann werden welche Log-Level verwendet?**

- **INFO:** Aufruf einer öffentlichen Methode.
- **WARNING:** Wenn ein Gehege oder Tier nicht gefunden wurde.
- **SEVERE:** Bei schwerwiegenden Fehlern oder inkonsistenten Zuständen.

---

### Streams

**Wo haben Streams geholfen?**

- Streams haben das Filtern und Sammeln der Tiere vereinfacht.
- Methoden wie `filter`, `map`, `flatMap` und `groupingBy` machen den Code kompakter.

**Wo wurden Streams unübersichtlich?**

- Längere Stream-Ketten sind teilweise schwerer zu lesen als klassische Schleifen.