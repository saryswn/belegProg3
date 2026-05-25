# CLI Befehlssatz

## Modi
- `:c` Wechsel in den Einfügemodus
- `:r` Wechsel in den Anzeigemodus
- `:u` Wechsel in den Änderungsmodus
- `:d` Wechsel in den Löschmodus
- `:x` beendet die Anwendung

## Einfügemodus (:c)
- `[K-Name]` fügt eine Kundschaft ein
- `DryBulkCargo [K-Name] [Wert] [Gefahrstoff] [GrainSize]` fügt ein Frachtstück ein

### Beispiele:
:c
Eren
DryBulkCargo Eren 200.00 FLAMMABLE 5
## Anzeigemodus (:r)
- `customers` zeigt alle Kunden mit Anzahl Frachtstücke
- `cargos` zeigt alle Frachtstücke
- `hazards i` zeigt vorhandene Gefahrenstoffe
- `hazards e` zeigt nicht vorhandene Gefahrenstoffe

## Änderungsmodus (:u)
- `[Lagerplatz]` setzt Inspektionsdatum auf jetzt

## Löschmodus (:d)
- `[Lagerplatz]` entfernt Frachtstück
- `[K-Name]` löscht Kundschaft

## alternativ konfiguriertes CLI (CLIAlt)
_deaktiviert: Löschen von Kunden, Anzeigen von Gefahrenstoffen_
_nur ein Beobachter aktiv: CapacityObserver_