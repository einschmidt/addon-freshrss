#!/command/with-contenv bashio
# shellcheck shell=bash
# ==============================================================================
# Home Assistant Add-on: FreshRSS
# ------------------------------------------------------------------------------
main() {
    bashio::log.trace "${FUNCNAME[0]}"

    while true; do
        /usr/bin/php /usr/share/freshrss/app/actualize_script.php
        sleep 1200
    done
}
main "$@"