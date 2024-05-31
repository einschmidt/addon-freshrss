ARG BUILD_FROM
# hadolint ignore=DL3006
FROM $BUILD_FROM

# Set shell
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

ARG FRESHRSS_VERSION="1.24.0"

# Setup base
# hadolint ignore=DL3003,DL3018
RUN \
    set -eux \
    \
    && apk add --no-cache --virtual .build-dependencies \
        git=2.45.1-r0 \
    \
    && apk add --no-cache \
        tzdata=2024a-r1 \
        nginx=1.26.1-r0 \
        php php-curl php-gmp php-intl php-mbstring php-xml php-zip \
        php-ctype php-dom php-fileinfo php-iconv php-json php-opcache \
        php-openssl php-phar php-session php-simplexml php-xmlreader \
        php-xmlwriter php-xml php-tokenizer php-zlib php-fpm\
        php-pdo_sqlite php-pdo_mysql php-pdo_pgsql \
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
