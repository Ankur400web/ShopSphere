function HeroSection() {
    return (
        <section className="bg-background">
            <div className="mx-auto grid max-w-7xl items-center gap-12 px-6 py-20 lg:grid-cols-2">
                {/* Content */}
                <div>
                    <span
                        className="text-sm font-semibold uppercase tracking-wider text-primary-600"
                    >
                            Shop smarter
                    </span>

                    <h1 className="mt-4 max-w-xl text-4xl font-bold tracking-tight text-text-primary sm:text-5xl lg:text-6xl">
                        Discover products you'll love.
                    </h1>

                    <p className="mt-6 max-w-lg text-lg leading-8 text-text-secondary">
                        Everything you need, carefully curated and delivered right to your door.
                    </p>

                    <div className="mt-8 flex flex-wrap gap-4">
                        <button
                            type="button"
                            className="rounded-lg bg-primary-600 px-6 py-3 font-semibold text-white transition-colors hover:bg-primary-700"
                        >
                            Shop Now
                        </button>

                        <button
                            type="button"
                            className="rounded-lg border border-border bg-surface px-6 py-3 font-semibold text-text-primary transition-colors hover:bg-background"
                        >
                            Explore Categories
                        </button>
                    </div>
                </div>

                {/* Visual */}
                <div className="relative">
                    <div className="rounded-3xl border border-border bg-surface p-8 shadow-sm">
                        <div className="flex aspect-square items-center justify-center rounded-2xl bg-primary-50">
                            <span className="text-8xl">🛍️</span>
                        </div>
                    </div>
                    <div className="absolute -bottom-4 -left-4 rounded-xl border border-border bg-surface px-4 py-3 shadow-lg">
                        <p className="text-sm font-semibold text-text-primary">
                            Free delivery
                        </p>
                        <p className="text-xs text-text-muted">
                            On orders over $50
                        </p>
                    </div>
                </div>

            </div>

        </section>
    );
}

export default HeroSection;