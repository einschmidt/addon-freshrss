ARG BUILD_FROM
# hadolint ignore=DL3006
FROM $BUILD_FROM

# Set shell
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

ARG FRESHRSS_VERSION="1.26.3"

# Setup base
# hadolint ignore=DL3003,DL3018
RUN \
    set -eux \
    \
    && apk add --no-cache --virtual .build-dependencies \
        git=2.49.1-r0 \
    \
    && apk add --no-cache \
        tzdata=2025b-r0 \
        nginx=1.28.0-r3 \
        php83 php83-curl php83-gmp php83-intl php83-mbstring php83-xml php83-zip \
        php83-ctype php83-dom php83-fileinfo php83-iconv php83-json php83-opcache \
        php83-openssl php83-phar php83-session php83-simplexml php83-xmlreader \
        php83-xmlwriter php83-tokenizer php83-zlib php83-fpm \
        php83-pdo_sqlite php83-pdo_mysql php83-pdo_pgsql \
    \
    && git clone --branch "$FRESHRSS_VERSION" --depth=1 \
        "https://github.com/FreshRSS/FreshRSS.git" /usr/share/freshrss \
    \
    # Disable built-in updates when using Docker, as the full image is supposed
    # to be updated instead.
    && sed -r -i "\\#disable_update#s#^.*#\t'disable_update' => true,#" \
        /usr/share/freshrss/config.default.php \
    \
    && rm -f -r \
        /etc/nginx \
        /etc/php8/php-fpm.d/www.conf \
    \
    && apk del --no-cache --purge \
        .build-dependencies

# Copy root filesystem
COPY rootfs /

# Build arguments
ARG BUILD_ARCH
ARG BUILD_DATE
ARG BUILD_DESCRIPTION
ARG BUILD_NAME
ARG BUILD_REF
ARG BUILD_REPOSITORY
ARG BUILD_VERSION

# Labels
LABEL \
    io.hass.name="${BUILD_NAME}" \
    io.hass.description="${BUILD_DESCRIPTION}" \
    io.hass.arch="${BUILD_ARCH}" \
    io.hass.type="addon" \
    io.hass.version=${BUILD_VERSION} \
    maintainer="Einschmidt" \
    org.opencontainers.image.title="${BUILD_NAME}" \
    org.opencontainers.image.description="${BUILD_DESCRIPTION}" \
    org.opencontainers.image.vendor="Einschmidt" \
    org.opencontainers.image.authors="einschmidt" \
    org.opencontainers.image.licenses="MIT" \
    org.opencontainers.image.url="https://google.com" \
    org.opencontainers.image.source="https://github.com/${BUILD_REPOSITORY}" \
    org.opencontainers.image.documentation="https://github.com/${BUILD_REPOSITORY}/blob/main/README.md" \
    org.opencontainers.image.created=${BUILD_DATE} \
    org.opencontainers.image.revision=${BUILD_REF} \
    org.opencontainers.image.version=${BUILD_VERSION}
