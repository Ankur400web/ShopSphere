import HeroSection from "../../components/common/HeroSection.tsx";
import CategorySection from "../../components/common/CategorySection";
import FeaturedProducts from "../../components/product/FeaturedProducts";

function HomePage() {
    return (
        <main>
            <HeroSection />
            <CategorySection />
            <FeaturedProducts />

        </main>
    );
}

export default HomePage;